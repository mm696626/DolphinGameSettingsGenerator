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

    public void saveINI(File tempSettingsFile) {
        PrintWriter outputStream = null;

        //pass in temp settings file to use file path to save to folder
        String settingsFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = settingsFilePath.lastIndexOf("settings.txt");
        String filePathSeparator = settingsFilePath.substring(fileNameIndex-1, fileNameIndex);
        String settingsFilePathFolder = settingsFilePath.substring(0, settingsFilePath.lastIndexOf(filePathSeparator));

        File gameSettingsFolder = new File(settingsFilePathFolder + filePathSeparator + "GameSettings");
        if (!gameSettingsFolder.exists()) {
            gameSettingsFolder.mkdirs();
        }

        try {
            outputStream = new PrintWriter(new FileOutputStream(settingsFilePathFolder + filePathSeparator + "GameSettings" + filePathSeparator + gameID + ".ini"));
        }
        catch (FileNotFoundException f) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        settingsBlocks = getSettingsBlocks();

        for (int i=0; i<settingsBlocks.size(); i++) {
            String iniSettingsForSettingBlock = runAppropriateSettingBlock(settingsBlocks.get(i));
            outputStream.print(iniSettingsForSettingBlock);
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
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.coreOptions, INIConfigNames.INICoreOptions);
        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.videoSettingsOptions, INIConfigNames.INIVideoSettingsOptions);
        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.videoEnhancementsOptions, INIConfigNames.INIVideoEnhancementsOptions);
        }

        else if (settingBlockHeader.equals("[Video_Hacks]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.videoHacksOptions, INIConfigNames.INIVideoHacksOptions);
        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.videoHardwareOption, INIConfigNames.INIVideoHardwareOption);
        }

        else if (settingBlockHeader.equals("[DSP]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.dspAudioOption, INIConfigNames.INIDspAudioOption);
        }

        else if (settingBlockHeader.equals("[Wii]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.wiiOptions, INIConfigNames.INIWiiOptions);
        }

        else if (settingBlockHeader.equals("[Controls]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.controlOptions, INIConfigNames.INIControlOptions);
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
        for (int i = 0; i< DifferingINIConfigOptions.differentConfigOptionNames.length; i++) {
            if (setting.equals(DifferingINIConfigOptions.differentConfigOptionNames[i])) {
                return findCorrespondingINIValue(settingValue, DifferingINIConfigOptions.differingConfigOptions[i], DifferingINIConfigOptions.differingINIConfigOptions[i]);
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
