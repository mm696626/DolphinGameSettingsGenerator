package io;

import constants.ConfigNames;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameSettingsSaver {

    public void saveGameSettingsToTempFile(ArrayList<JComboBox> coreJComboBoxes
            , ArrayList<JComboBox> videoSettingsJComboBoxes
            , ArrayList<JComboBox> videoEnhancementsJComboBoxes
            , ArrayList<JComboBox> videoHacksJComboBoxes
            , JComboBox videoHardwareJComboBox
            , JSlider dspAudioVolumeSlider
            , ArrayList<JComboBox> wiiJComboBoxes
            , ArrayList<JComboBox> controlJComboBoxes
            , ArrayList<JTextField> controlJTextFields) {

        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter( new FileOutputStream("settings.txt"));
        }
        catch (FileNotFoundException f) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        String coreSettings = saveSettings(coreJComboBoxes,  "Core", ConfigNames.coreOptions);
        String videoSettings = saveSettings(videoSettingsJComboBoxes, "Video_Settings", ConfigNames.videoSettingsOptions);
        String videoEnhancementSettings = saveSettings(videoEnhancementsJComboBoxes, "Video_Enhancements",  ConfigNames.videoEnhancementsOptions);
        String videoHacksSettings = saveSettings(videoHacksJComboBoxes, "Video_Hacks", ConfigNames.videoHacksOptions);
        String videoHardwareSetting = saveVideoHardwareSetting(videoHardwareJComboBox);
        String dspAudioSetting = saveDSPAudioSetting(dspAudioVolumeSlider);
        String wiiSettings = saveSettings(wiiJComboBoxes, "Wii", ConfigNames.wiiOptions);
        String controlSettings = saveControlSettings(controlJComboBoxes, controlJTextFields);


        if (coreSettings.length() > 0) {
            outputStream.println(coreSettings);
        }

        if (videoSettings.length() > 0) {
            outputStream.println(videoSettings);
        }

        if (videoEnhancementSettings.length() > 0) {
            outputStream.println(videoEnhancementSettings);
        }

        if (videoHacksSettings.length() > 0) {
            outputStream.println(videoHacksSettings);
        }

        if (videoHardwareSetting.length() > 0) {
            outputStream.println(videoHardwareSetting);
        }

        if (dspAudioSetting.length() > 0) {
            outputStream.println(dspAudioSetting);
        }

        if (wiiSettings.length() > 0) {
            outputStream.println(wiiSettings);
        }

        if (controlSettings.length() > 0) {
            outputStream.println(controlSettings);
        }

        outputStream.close();
    }

    private String saveSettings(ArrayList<JComboBox> jComboBoxes, String settingsHeader, String[] options) {
        String settings = "";
        boolean isChanged = false;
        for (int i=0; i<jComboBoxes.size(); i++) {
            if (jComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            settings += "[Core]\n";
            for (int i=0; i<jComboBoxes.size(); i++) {
                if (jComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.coreOptions[i] + "=" + jComboBoxes.get(i).getItemAt(jComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    settings += settingValue;
                }
            }
        }

        return settings;
    }

    private String saveVideoHardwareSetting(JComboBox videoHardwareJComboBox) {
        String videoHardwareSetting = "";
        boolean isChanged = false;
        if (videoHardwareJComboBox.getSelectedIndex() != 0) {
            isChanged = true;
        }

        if (!isChanged) {
            return "";
        }

        else {
            videoHardwareSetting += "[Video_Hardware]\n";
            if (videoHardwareJComboBox.getSelectedIndex() != 0) {
                String settingValue = ConfigNames.videoHacksOptions[0] + "=" + videoHardwareJComboBox.getItemAt(videoHardwareJComboBox.getSelectedIndex()).toString() + "\n";
                videoHardwareSetting += settingValue;
            }
        }

        return videoHardwareSetting;
    }

    private String saveDSPAudioSetting(JSlider dspAudioVolumeSlider) {
        String dspAudioSetting = "";
        boolean isChanged = false;
        if (dspAudioVolumeSlider.getValue() != 100) {
            isChanged = true;
        }

        if (!isChanged) {
            return "";
        }

        else {
            dspAudioSetting += "[DSP]\n";
            String settingValue = ConfigNames.dspAudioOption[0] + "=" + dspAudioVolumeSlider.getValue() + "\n";
            dspAudioSetting += settingValue;
        }

        return dspAudioSetting;
    }

    private String saveControlSettings(ArrayList<JComboBox> controlJComboBoxes, ArrayList<JTextField> controlJTextFields) {
        String controlSettings = "";
        boolean isChanged = false;
        for (int i=0; i<controlJComboBoxes.size(); i++) {
            if (controlJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            controlSettings += "[Controls]\n";
            int comboBoxesChecked = 0;
            int textFieldsChecked = 0;

            for (int i=0; i<ConfigNames.controlOptions.length; i++) {

                if (!ConfigNames.controlOptions[i].contains("Profile")) {
                    if (controlJComboBoxes.get(comboBoxesChecked).getSelectedIndex() != 0) {
                        String settingValue = ConfigNames.controlOptions[i] + "=" + controlJComboBoxes.get(comboBoxesChecked).getItemAt(controlJComboBoxes.get(comboBoxesChecked).getSelectedIndex()).toString() + "\n";
                        controlSettings += settingValue;
                        comboBoxesChecked++;
                    }
                }
                else {
                    if (controlJTextFields.get(textFieldsChecked).getText().trim().length() > 0) {
                        String settingValue = ConfigNames.controlOptions[i] + "=" + controlJTextFields.get(textFieldsChecked).getText().trim() + "\n";
                        controlSettings += settingValue;
                        textFieldsChecked++;
                    }
                }

            }
        }

        return controlSettings;
    }
}
