package io;

import constants.ConfigNames;
import constants.ConfigOptions;
import constants.INIConfigNames;
import constants.INIConfigOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSettingINISaver {

    private String gameID = "";
    private ArrayList<String> settingsBlocks = new ArrayList<>();

    public GameSettingINISaver(String gameID) {
        this.gameID = gameID;
    }

    public void saveINI() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter( new FileOutputStream(gameID + ".ini"));
        }
        catch (FileNotFoundException f) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        settingsBlocks = getSettingsBlocks();

        for (int i=0; i<settingsBlocks.size(); i++) {
            String iniSettingsForSettingBlock = runAppropriateSettingBlock(settingsBlocks.get(i));
            outputStream.println(iniSettingsForSettingBlock);
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
            return getCoreSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {
            return getVideoSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {
            return "hello";
        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {
            return "hello";
        }

        else if (settingBlockHeader.equals("[DSP]")) {
            return "hello";
        }

        else if (settingBlockHeader.equals("[Wii]")) {
            return "hello";
        }

        else if (settingBlockHeader.equals("[Controls]")) {
            return "hello";
        }

        else {
            return "";
        }

    }

    private String getCoreSettingsBlockInINIForm(String coreSettingsBlock) {
        String[] coreSettings = coreSettingsBlock.split("\n");
        String coreSettingsBlockINI = "";
        for (int i=1; i<coreSettings.length; i++) {
            String coreSetting = getCorrespondingINISetting(coreSettings[i].split("=")[0], ConfigNames.coreOptions, INIConfigNames.INICoreOptions);
            String coreSettingValue = getCorrespondingCoreINISettingValue(coreSettings[i].split("=")[0], coreSettings[i].split("=")[1]);
            coreSettingsBlockINI += coreSetting + "=" + coreSettingValue + "\n";
        }

        return coreSettingsBlockINI;
    }

    private String getVideoSettingsBlockInINIForm(String videoSettingsBlock) {
        String[] videoSettings = videoSettingsBlock.split("\n");
        String videoSettingsBlockINI = "";
        for (int i=1; i<videoSettings.length; i++) {
            String videoSetting = getCorrespondingINISetting(videoSettings[i].split("=")[0], ConfigNames.videoSettingsOptions, INIConfigNames.INIVideoSettingsOptions);
            String videoSettingValue = getCorrespondingVideoINISettingValue(videoSettings[i].split("=")[0], videoSettings[i].split("=")[1]);
            videoSettingsBlockINI += videoSetting + "=" + videoSettingValue + "\n";
        }

        return videoSettingsBlockINI;
    }

    private String getCorrespondingINISetting(String coreSetting, String[] optionNames, String[] iniOptions) {
        for (int i=0; i<iniOptions.length; i++) {
            if (coreSetting.equals(optionNames[i])) {
                return iniOptions[i];
            }
        }

        return null;
    }

    private String getCorrespondingCoreINISettingValue(String coreSetting, String coreSettingValue) {

        int index = 0;

        if (coreSetting.equals("Graphics Backend")) {

            for (int i=0; i<ConfigOptions.graphicBackends.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.graphicBackends[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.graphicBackends[index];
        }

        else if (coreSetting.equals("CPU Emulator Engine")) {
            for (int i=0; i<ConfigOptions.cpuEmulatorEngine.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.cpuEmulatorEngine[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.cpuEmulatorEngine[index];
        }

        else if (coreSetting.equals("Emulation Speed")) {
            for (int i=0; i<ConfigOptions.emulationSpeeds.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.emulationSpeeds[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.emulationSpeeds[index];
        }

        else if (coreSetting.equals("CPU Overclock") || coreSetting.equals("GPU Overclock") ) {
            for (int i=0; i<ConfigOptions.overClockSpeeds.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.overClockSpeeds[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.overClockSpeeds[index];
        }

        else if (coreSetting.equals("GameCube Language")) {
            for (int i=0; i<ConfigOptions.gameCubeLanguage.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.gameCubeLanguage[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.gameCubeLanguage[index];
        }

        else if (coreSetting.equals("GameCube Slot A") || coreSetting.equals("GameCube Slot B")) {
            for (int i=0; i<ConfigOptions.gameCubeSlot.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.gameCubeSlot[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.gameCubeSlot[index];
        }

        else if (coreSetting.equals("GameCube Serial Port")) {
            for (int i=0; i<ConfigOptions.gameCubeSerialPort.length; i++)  {
                if (coreSettingValue.equals(ConfigOptions.gameCubeSerialPort[i])) {
                    index = i;
                    break;
                }
            }

            coreSettingValue = INIConfigOptions.gameCubeSerialPort[index];
        }


        return coreSettingValue;
    }

    private String getCorrespondingVideoINISettingValue(String videoSetting, String videoSettingValue) {

        int index = 0;

        if (videoSetting.equals("Internal Resolution")) {

            for (int i=0; i<ConfigOptions.internalResolutions.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.internalResolutions[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.internalResolutions[index];
        }

        else if (videoSetting.equals("Shader Compilation")) {
            for (int i=0; i<ConfigOptions.shaderCompilationMethods.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.shaderCompilationMethods[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.shaderCompilationMethods[index];
        }

        else if (videoSetting.equals("Texture Cache Accuracy")) {
            for (int i=0; i<ConfigOptions.textureCacheAccuracies.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.textureCacheAccuracies[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.textureCacheAccuracies[index];
        }

        else if (videoSetting.equals("Aspect Ratio")) {
            for (int i=0; i<ConfigOptions.aspectRatios.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.aspectRatios[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.aspectRatios[index];
        }

        else if (videoSetting.equals("Anti-Aliasing")) {
            for (int i=0; i<ConfigOptions.antiAliasing.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.antiAliasing[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.antiAliasing[index];
        }

        else if (videoSetting.equals("Anti-Aliasing Method")) {
            for (int i=0; i<ConfigOptions.antiAliasingMethods.length; i++)  {
                if (videoSettingValue.equals(ConfigOptions.antiAliasingMethods[i])) {
                    index = i;
                    break;
                }
            }

            videoSettingValue = INIConfigOptions.antiAliasingMethods[index];
        }


        return videoSettingValue;
    }
}
