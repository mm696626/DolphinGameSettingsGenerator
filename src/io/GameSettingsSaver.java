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

        String coreSettings = saveCoreSettings(coreJComboBoxes);
        outputStream.println(coreSettings);

        String videoSettings = saveVideoSettings(videoSettingsJComboBoxes);
        outputStream.println(videoSettings);

        String videoEnhancementSettings = saveVideoEnhancementSettings(videoEnhancementsJComboBoxes);
        outputStream.println(videoEnhancementSettings);

        String videoHacksSettings = saveVideoHackSettings(videoHacksJComboBoxes);
        outputStream.println(videoHacksSettings);

        String videoHardwareSetting = saveVideoHardwareSetting(videoHardwareJComboBox);
        outputStream.println(videoHardwareSetting);

        String dspAudioSetting = saveDSPAudioSetting(dspAudioVolumeSlider);
        outputStream.println(dspAudioSetting);

        String wiiSettings = saveWiiSettings(wiiJComboBoxes);
        outputStream.println(wiiSettings);

        String controlSettings = saveControlSettings(controlJComboBoxes, controlJTextFields);
        outputStream.println(controlSettings);

        outputStream.close();
    }

    private String saveCoreSettings(ArrayList<JComboBox> coreJComboBoxes) {
        String coreSettings = "";
        boolean isChanged = false;
        for (int i=0; i<coreJComboBoxes.size(); i++) {
            if (coreJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            coreSettings += "[Core]\n";
            for (int i=0; i<coreJComboBoxes.size(); i++) {
                if (coreJComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.coreOptions[i] + "=" + coreJComboBoxes.get(i).getItemAt(coreJComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    coreSettings += settingValue;
                }
            }
        }

        return coreSettings;
    }

    private String saveVideoSettings(ArrayList<JComboBox> videoSettingsJComboBoxes) {
        String videoSettings = "";
        boolean isChanged = false;
        for (int i=0; i<videoSettingsJComboBoxes.size(); i++) {
            if (videoSettingsJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            videoSettings += "[Video_Settings]\n";
            for (int i=0; i<videoSettingsJComboBoxes.size(); i++) {
                if (videoSettingsJComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.videoSettingsOptions[i] + "=" + videoSettingsJComboBoxes.get(i).getItemAt(videoSettingsJComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    videoSettings += settingValue;
                }
            }
        }

        return videoSettings;
    }

    private String saveVideoEnhancementSettings(ArrayList<JComboBox> videoEnhancementsJComboBoxes) {
        String videoEnhancementSettings = "";
        boolean isChanged = false;
        for (int i=0; i<videoEnhancementsJComboBoxes.size(); i++) {
            if (videoEnhancementsJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            videoEnhancementSettings += "[Video_Enhancements]\n";
            for (int i=0; i<videoEnhancementsJComboBoxes.size(); i++) {
                if (videoEnhancementsJComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.videoEnhancementsOptions[i] + "=" + videoEnhancementsJComboBoxes.get(i).getItemAt(videoEnhancementsJComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    videoEnhancementSettings += settingValue;
                }
            }
        }

        return videoEnhancementSettings;
    }

    private String saveVideoHackSettings(ArrayList<JComboBox> videoHacksJComboBoxes) {
        String videoHacksSettings = "";
        boolean isChanged = false;
        for (int i=0; i<videoHacksJComboBoxes.size(); i++) {
            if (videoHacksJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            videoHacksSettings += "[Video_Hacks]\n";
            for (int i=0; i<videoHacksJComboBoxes.size(); i++) {
                if (videoHacksJComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.videoHacksOptions[i] + "=" + videoHacksJComboBoxes.get(i).getItemAt(videoHacksJComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    videoHacksSettings += settingValue;
                }
            }
        }

        return videoHacksSettings;
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

    private String saveWiiSettings(ArrayList<JComboBox> wiiJComboBoxes) {
        String wiiSettings = "";
        boolean isChanged = false;
        for (int i=0; i<wiiJComboBoxes.size(); i++) {
            if (wiiJComboBoxes.get(i).getSelectedIndex() != 0) {
                isChanged = true;
            }
        }

        if (!isChanged) {
            return "";
        }

        else {
            wiiSettings += "[Wii]\n";
            for (int i=0; i<wiiJComboBoxes.size(); i++) {
                if (wiiJComboBoxes.get(i).getSelectedIndex() != 0) {
                    String settingValue = ConfigNames.wiiOptions[i] + "=" + wiiJComboBoxes.get(i).getItemAt(wiiJComboBoxes.get(i).getSelectedIndex()).toString() + "\n";
                    wiiSettings += settingValue;
                }
            }
        }

        return wiiSettings;
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
