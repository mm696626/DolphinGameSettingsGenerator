package io;

import constants.ConfigNames;
import constants.DifferingINIConfigOptions;
import constants.INIConfigNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSettingINISaver {

    private String gameID = "";
    private ArrayList<String> settingsBlocks = new ArrayList<>();
    private ArrayList<String> coreOtherLines;
    private ArrayList<String> videoSettingsOtherLines;
    private ArrayList<String> videoEnhancementsOtherLines;
    private ArrayList<String> videoHacksOtherLines;
    private ArrayList<String> videoHardwareOtherLines;
    private ArrayList<String> dspOtherLines;
    private ArrayList<String> wiiOtherLines;
    private ArrayList<String> controlsOtherLines;

    public GameSettingINISaver(String gameID) {
        this.gameID = gameID;
    }

    public void saveINI(boolean isEditing, File tempSettingsFile, String iniFilePath, ArrayList<String> otherLines, ArrayList<String> coreOtherLines, ArrayList<String> videoSettingsOtherLines, ArrayList<String> videoEnhancementsOtherLines, ArrayList<String> videoHacksOtherLines, ArrayList<String> videoHardwareOtherLines, ArrayList<String> dspOtherLines, ArrayList<String> wiiOtherLines, ArrayList<String> controlsOtherLines) throws IOException {
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

        String iniSaveDirectory = "";

        if (gameID == null) {
            outputStream = new PrintWriter(iniFilePath);
            iniSaveDirectory = iniFilePath;
        }
        else {
            try {
                outputStream = new PrintWriter(new FileOutputStream(settingsFilePathFolder + filePathSeparator + "GameSettings" + filePathSeparator + gameID + ".ini"));
                iniSaveDirectory = settingsFilePathFolder + filePathSeparator + "GameSettings" + filePathSeparator + gameID + ".ini";
            }
            catch (FileNotFoundException f) {
                System.out.println("File does not exist");
                System.exit(0);
            }
        }

        settingsBlocks = getSettingsBlocks();

        this.coreOtherLines = coreOtherLines;
        this.videoSettingsOtherLines = videoSettingsOtherLines;
        this.videoEnhancementsOtherLines = videoEnhancementsOtherLines;
        this.videoHacksOtherLines = videoHacksOtherLines;
        this.videoHardwareOtherLines = videoHardwareOtherLines;
        this.dspOtherLines = dspOtherLines;
        this.wiiOtherLines = wiiOtherLines;
        this.controlsOtherLines = controlsOtherLines;

        for (int i=0; i<settingsBlocks.size(); i++) {
            String iniSettingsForSettingBlock = runAppropriateSettingBlock(settingsBlocks.get(i));
            outputStream.print(iniSettingsForSettingBlock);
        }

        if (isEditing) {
            if (!isInArrayList("[Core]", settingsBlocks) && !coreOtherLines.isEmpty())  {
                outputStream.println("[Core]");

                for (int i=0; i<coreOtherLines.size(); i++) {
                    outputStream.println(coreOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Video_Settings]", settingsBlocks) && !videoSettingsOtherLines.isEmpty())  {
                outputStream.println("[Video_Settings]");

                for (int i=0; i<videoSettingsOtherLines.size(); i++) {
                    outputStream.println(videoSettingsOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Video_Enhancements]", settingsBlocks) && !videoEnhancementsOtherLines.isEmpty())  {
                outputStream.println("[Video_Enhancements]");

                for (int i=0; i<videoEnhancementsOtherLines.size(); i++) {
                    outputStream.println(videoEnhancementsOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Video_Hacks]", settingsBlocks) && !videoHacksOtherLines.isEmpty())  {
                outputStream.println("[Video_Hacks]");

                for (int i=0; i<videoHacksOtherLines.size(); i++) {
                    outputStream.println(videoHacksOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Video_Hardware]", settingsBlocks) && !videoHardwareOtherLines.isEmpty())  {
                outputStream.println("[Video_Hardware]");

                for (int i=0; i<videoHardwareOtherLines.size(); i++) {
                    outputStream.println(videoHardwareOtherLines.get(i));
                }
            }

            if (!isInArrayList("[DSP]", settingsBlocks) && !dspOtherLines.isEmpty())  {
                outputStream.println("[DSP]");

                for (int i=0; i<dspOtherLines.size(); i++) {
                    outputStream.println(dspOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Wii]", settingsBlocks) && !wiiOtherLines.isEmpty())  {
                outputStream.println("[Wii]");

                for (int i=0; i<wiiOtherLines.size(); i++) {
                    outputStream.println(wiiOtherLines.get(i));
                }
            }

            if (!isInArrayList("[Controls]", settingsBlocks) && !controlsOtherLines.isEmpty())  {
                outputStream.println("[Controls]");

                for (int i=0; i<controlsOtherLines.size(); i++) {
                    outputStream.println(controlsOtherLines.get(i));
                }
            }

            for (int i=0; i<otherLines.size(); i++) {
                outputStream.println(otherLines.get(i));
            }
        }

        outputStream.close();

        GeneratorSettingsLoader generatorSettingsLoader = new GeneratorSettingsLoader();
        String autoMovePath = generatorSettingsLoader.getAutoMovePath();

        if (!autoMovePath.isEmpty()) {
            File userFolder = new File(autoMovePath);
            if (userFolder.exists()) {
                String gameSettingsPath = userFolder.getAbsolutePath();
                File userFolderGameSettingsFolder = new File(gameSettingsPath + filePathSeparator + "GameSettings");
                File iniFile = new File(iniSaveDirectory);
                File copiedSettingsFile = new File(userFolderGameSettingsFolder.getAbsolutePath() + filePathSeparator + iniFile.getName());
                Files.copy(iniFile.toPath(), copiedSettingsFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            }
        }
    }

    private boolean isInArrayList(String string, ArrayList<String> arrayList) {
        for (int i=0; i<arrayList.size(); i++) {
            if (arrayList.get(i).contains(string)) {
                return true;
            }
        }

        return false;
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

        inputStream.close();
        return settingsBlocks;
    }

    private String runAppropriateSettingBlock(String settingBlock) {
        String settingBlockHeader = settingBlock.split("\n")[0];

        if (settingBlockHeader.equals("[Core]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.CORE_OPTIONS, INIConfigNames.INI_CORE_OPTIONS) + addOtherLinesToBlock(coreOtherLines);
        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_SETTINGS_OPTIONS, INIConfigNames.INI_VIDEO_SETTINGS_OPTIONS) + addOtherLinesToBlock(videoSettingsOtherLines);
        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS, INIConfigNames.INI_VIDEO_ENHANCEMENTS_OPTIONS) + addOtherLinesToBlock(videoEnhancementsOtherLines);
        }

        else if (settingBlockHeader.equals("[Video_Hacks]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_HACKS_OPTIONS, INIConfigNames.INI_VIDEO_HACKS_OPTIONS) + addOtherLinesToBlock(videoHacksOtherLines);
        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.VIDEO_HARDWARE_OPTION, INIConfigNames.INI_VIDEO_HARDWARE_OPTION) + addOtherLinesToBlock(videoHardwareOtherLines);
        }

        else if (settingBlockHeader.equals("[DSP]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.DSP_AUDIO_OPTION, INIConfigNames.INI_DSP_AUDIO_OPTION) + addOtherLinesToBlock(dspOtherLines);
        }

        else if (settingBlockHeader.equals("[Wii]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.WII_OPTIONS, INIConfigNames.INI_WII_OPTIONS) + addOtherLinesToBlock(wiiOtherLines);
        }

        else if (settingBlockHeader.equals("[Controls]")) {
            return settingBlockHeader + "\n" + getSettingsBlockInINIForm(settingBlock, ConfigNames.CONTROL_OPTIONS, INIConfigNames.INI_CONTROL_OPTIONS) + addOtherLinesToBlock(controlsOtherLines);
        }

        else {
            return "";
        }

    }

    private String addOtherLinesToBlock(ArrayList<String> otherLines) {
        String otherLinesBlock = "";
        for (int i=0; i<otherLines.size(); i++) {
            otherLinesBlock += otherLines.get(i) + "\n";
        }

        return otherLinesBlock;
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
