package io;

import constants.ConfigNames;
import constants.DifferingINIConfigOptions;
import constants.INIConfigNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSettingINISaver {

    private String gameID = "";
    private ArrayList<String> settingsBlocks = new ArrayList<>();

    public GameSettingINISaver(String gameID) {
        this.gameID = gameID;
    }

    public void saveINI(File tempSettingsFile, String iniFilePath, ArrayList<String> otherLines) throws FileNotFoundException {
        PrintWriter outputStream = null;

        //pass in temp settings file to use file path to save to folder
        String settingsFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = settingsFilePath.lastIndexOf("settings.txt");
        String filePathSeparator = settingsFilePath.substring(fileNameIndex-1, fileNameIndex);
        String settingsFilePathFolder = settingsFilePath.substring(0, settingsFilePath.lastIndexOf(filePathSeparator));

        File gameSettingsFolder = new File(settingsFilePathFolder + filePathSeparator + "GameSettings");
        if (!gameSettingsFolder.exists() && gameID != null) {
            gameSettingsFolder.mkdirs();
        }

        if (gameID == null) {
            outputStream = new PrintWriter(iniFilePath);
        }
        else {
            try {
                outputStream = new PrintWriter(new FileOutputStream(settingsFilePathFolder + filePathSeparator + "GameSettings" + filePathSeparator + gameID + ".ini"));
            }
            catch (FileNotFoundException f) {
                System.out.println("File does not exist");
                System.exit(0);
            }
        }


        settingsBlocks = getSettingsBlocks();

        for (int i=0; i<settingsBlocks.size(); i++) {
            String iniSettingsForSettingBlock = runAppropriateSettingBlock(settingsBlocks.get(i));
            outputStream.print(iniSettingsForSettingBlock);
        }

        for (int i=0; i<otherLines.size(); i++) {
            outputStream.println(otherLines.get(i));
        }

        outputStream.close();
    }

    private ArrayList<String> getSettingsBlocks() {
        ArrayList<String> settingsBlocks = new ArrayList<>();

        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("settings.txt"));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        String settingBlock = "";
        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();

            if (line.equals("END OF SETTINGS BLOCK")) {
                settingsBlocks.add(settingBlock);
                settingBlock = "";
            }
            else {
                settingBlock += line + "\n";
            }
        }

        return settingsBlocks;
    }

    private String runAppropriateSettingBlock(String settingBlock) {
        String settingBlockHeader = settingBlock.split("\n")[0];

        if (settingBlockHeader.equals("[Core]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.CORE_OPTIONS, INIConfigNames.INI_CORE_OPTIONS);
        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_SETTINGS_OPTIONS, INIConfigNames.INI_VIDEO_SETTINGS_OPTIONS);
        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS, INIConfigNames.INI_VIDEO_ENHANCEMENTS_OPTIONS);
        }

        else if (settingBlockHeader.equals("[Video_Hacks]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_HACKS_OPTIONS, INIConfigNames.INI_VIDEO_HACKS_OPTIONS);
        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_HARDWARE_OPTION, INIConfigNames.INI_VIDEO_HARDWARE_OPTION);
        }

        else if (settingBlockHeader.equals("[DSP]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.DSP_AUDIO_OPTION, INIConfigNames.INI_DSP_AUDIO_OPTION);
        }

        else if (settingBlockHeader.equals("[Wii]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.WII_OPTIONS, INIConfigNames.INI_WII_OPTIONS);
        }

        else if (settingBlockHeader.equals("[Controls]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.CONTROL_OPTIONS, INIConfigNames.INI_CONTROL_OPTIONS);
        }

        else {
            return "";
        }

    }

    private String getSettingsBlockInINIForm(String settingsBlock, String[] options, String[] iniOptions) {
        String[] settingsBlockSettings = settingsBlock.split("\n");
        String settingsBlockINI = "";
        for (int i=1; i<settingsBlockSettings.length; i++) {

            String settingName = settingsBlockSettings[i].split("=")[0];

            if (settingName.equals("CPU Overclock")) {
                settingsBlockINI += "OverclockEnable=True" + "\n";
            }

            String setting = getCorrespondingINISetting(settingName, options, iniOptions);
            String iniSettingValue = getCorrespondingINISettingValue(settingName, settingsBlockSettings[i].split("=")[1]);
            settingsBlockINI += setting + "=" + iniSettingValue + "\n";
        }

        return settingsBlockINI;
    }

    private String getCorrespondingINISetting(String coreSetting, String[] optionNames, String[] iniOptions) {
        for (int i=0; i<iniOptions.length; i++) {
            if (coreSetting.equals(optionNames[i])) {
                return iniOptions[i];
            }
        }

        return null;
    }

    private String getCorrespondingINISettingValue(String setting, String settingValue) {
        for (int i = 0; i< DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES.length; i++) {
            if (setting.equals(DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES[i])) {
                return findCorrespondingINIValue(settingValue, DifferingINIConfigOptions.DIFFERING_CONFIG_OPTIONS[i], DifferingINIConfigOptions.DIFFERING_INI_CONFIG_OPTIONS[i]);
            }
        }

        return settingValue;
    }

    private String findCorrespondingINIValue(String settingValue, String[] options, String[] iniOptions) {
        int index = 0;

        for (int i=0; i<options.length; i++)  {
            if (settingValue.equals(options[i])) {
                index = i;
                break;
            }
        }

       return iniOptions[index];
    }
}
