package io;

import constants.ConfigNames;
import constants.ConfigOptions;
import constants.INIConfigNames;
import constants.INIConfigOptions;

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
            return settingBlockHeader + "\n" + getCoreSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {
            return settingBlockHeader + "\n" + getVideoSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {
            return settingBlockHeader + "\n" + getVideoEnhancementSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {
            return settingBlockHeader + "\n" + getVideoHardwareSettingBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[DSP]")) {
            return settingBlockHeader + "\n" + getDSPAudioSettingBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Wii]")) {
            return settingBlockHeader + "\n" + getWiiSettingsBlockInINIForm(settingBlock);
        }

        else if (settingBlockHeader.equals("[Controls]")) {
            return settingBlockHeader + "\n" + getControlSettingsBlockInINIForm(settingBlock);
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

    private String getVideoEnhancementSettingsBlockInINIForm(String videoEnhancementSettingsBlock) {
        String[] videoEnhancementSettings = videoEnhancementSettingsBlock.split("\n");
        String videoEnhancementSettingsBlockINI = "";
        for (int i=1; i<videoEnhancementSettings.length; i++) {
            String videoEnhancementSetting = getCorrespondingINISetting(videoEnhancementSettings[i].split("=")[0], ConfigNames.videoEnhancementsOptions, INIConfigNames.INIVideoEnhancementsOptions);
            String videoEnhancementSettingValue = getCorrespondingVideoEnhancementINISettingValue(videoEnhancementSettings[i].split("=")[0], videoEnhancementSettings[i].split("=")[1]);
            videoEnhancementSettingsBlockINI += videoEnhancementSetting + "=" + videoEnhancementSettingValue + "\n";
        }

        return videoEnhancementSettingsBlockINI;
    }

    private String getVideoHardwareSettingBlockInINIForm(String videoHardwareSettingBlock) {
        String[] videoEnhancementSettings = videoHardwareSettingBlock.split("\n");
        String videoEnhancementSettingsBlockINI = "";
        for (int i=1; i<videoEnhancementSettings.length; i++) {
            String videoEnhancementSetting = getCorrespondingINISetting(videoEnhancementSettings[i].split("=")[0], ConfigNames.videoHardwareOption, INIConfigNames.INIVideoHardwareOption);
            String videoEnhancementSettingValue = videoEnhancementSettings[i].split("=")[1];
            videoEnhancementSettingsBlockINI += videoEnhancementSetting + "=" + videoEnhancementSettingValue + "\n";
        }

        return videoEnhancementSettingsBlockINI;
    }

    private String getDSPAudioSettingBlockInINIForm(String dspAudioSettingBlock) {
        String[] dspAudioSettings = dspAudioSettingBlock.split("\n");
        String dspAudioSettingsBlockINI = "";
        for (int i=1; i<dspAudioSettings.length; i++) {
            String dspAudioSetting = getCorrespondingINISetting(dspAudioSettings[i].split("=")[0], ConfigNames.dspAudioOption, INIConfigNames.INIDspAudioOption);
            String dspAudioSettingValue = dspAudioSettings[i].split("=")[1];
            dspAudioSettingsBlockINI += dspAudioSetting + "=" + dspAudioSettingValue + "\n";
        }

        return dspAudioSettingsBlockINI;
    }

    private String getWiiSettingsBlockInINIForm(String wiiSettingsBlock) {
        String[] wiiSettings = wiiSettingsBlock.split("\n");
        String wiiSettingsBlockINI = "";
        for (int i=1; i<wiiSettings.length; i++) {
            String wiiSetting = getCorrespondingINISetting(wiiSettings[i].split("=")[0], ConfigNames.wiiOptions, INIConfigNames.INIWiiOptions);
            String wiiSettingValue = getCorrespondingWiiINISettingValue(wiiSettings[i].split("=")[0], wiiSettings[i].split("=")[1]);
            wiiSettingsBlockINI += wiiSetting + "=" + wiiSettingValue + "\n";
        }

        return wiiSettingsBlockINI;
    }

    private String getControlSettingsBlockInINIForm(String controlSettingsBlock) {
        String[] controlSettings = controlSettingsBlock.split("\n");
        String controlSettingsBlockINI = "";
        for (int i=1; i<controlSettings.length; i++) {
            String wiiSetting = getCorrespondingINISetting(controlSettings[i].split("=")[0], ConfigNames.controlOptions, INIConfigNames.INIControlOptions);
            String wiiSettingValue = getCorrespondingControlINISettingValue(controlSettings[i].split("=")[0], controlSettings[i].split("=")[1]);
            controlSettingsBlockINI += wiiSetting + "=" + wiiSettingValue + "\n";
        }

        return controlSettingsBlockINI;
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

    private String getCorrespondingVideoEnhancementINISettingValue(String videoEnhancementSetting, String videoEnhancementSettingValue) {

        int index = 0;

        if (videoEnhancementSetting.equals("Anisotropic Filtering")) {

            for (int i=0; i<ConfigOptions.anisotropicFiltering.length; i++)  {
                if (videoEnhancementSettingValue.equals(ConfigOptions.anisotropicFiltering[i])) {
                    index = i;
                    break;
                }
            }

            videoEnhancementSettingValue = INIConfigOptions.anisotropicFiltering[index];
        }

        else if (videoEnhancementSetting.equals("Force Texture Filtering")) {
            for (int i=0; i<ConfigOptions.textureFiltering.length; i++)  {
                if (videoEnhancementSettingValue.equals(ConfigOptions.textureFiltering[i])) {
                    index = i;
                    break;
                }
            }

            videoEnhancementSettingValue = INIConfigOptions.textureFiltering[index];
        }


        return videoEnhancementSettingValue;
    }

    private String getCorrespondingWiiINISettingValue(String wiiSetting, String wiiSettingValue) {

        int index = 0;

        if (wiiSetting.equals("Wii Language")) {

            for (int i=0; i<ConfigOptions.wiiLanguage.length; i++)  {
                if (wiiSettingValue.equals(ConfigOptions.wiiLanguage[i])) {
                    index = i;
                    break;
                }
            }

            wiiSettingValue = INIConfigOptions.wiiLanguage[index];
        }

        else if (wiiSetting.equals("Wii Aspect Ratio")) {
            for (int i=0; i<ConfigOptions.wiiAspectRatio.length; i++)  {
                if (wiiSettingValue.equals(ConfigOptions.wiiAspectRatio[i])) {
                    index = i;
                    break;
                }
            }

            wiiSettingValue = INIConfigOptions.wiiAspectRatio[index];
        }


        return wiiSettingValue;
    }

    private String getCorrespondingControlINISettingValue(String controlSetting, String controlSettingValue) {

        int index = 0;

        if (controlSetting.equals("GameCube Controller Port 1")
            || controlSetting.equals("GameCube Controller Port 2")
            || controlSetting.equals("GameCube Controller Port 3")
            || controlSetting.equals("GameCube Controller Port 4")) {

            for (int i=0; i<ConfigOptions.gameCubeControllerTypes.length; i++)  {
                if (controlSettingValue.equals(ConfigOptions.gameCubeControllerTypes[i])) {
                    index = i;
                    break;
                }
            }

            controlSettingValue = INIConfigOptions.gameCubeControllerTypes[index];
        }

        else if (controlSetting.equals("Wii Remote 1")
                || controlSetting.equals("Wii Remote 2")
                || controlSetting.equals("Wii Remote 3")
                || controlSetting.equals("Wii Remote 4")) {
            for (int i=0; i<ConfigOptions.wiiControllerTypes.length; i++)  {
                if (controlSettingValue.equals(ConfigOptions.wiiControllerTypes[i])) {
                    index = i;
                    break;
                }
            }

            controlSettingValue = INIConfigOptions.wiiControllerTypes[index];
        }


        return controlSettingValue;
    }
}
