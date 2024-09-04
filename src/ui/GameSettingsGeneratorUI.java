package ui;

import constants.ConfigNames;
import constants.ConfigOptions;
import constants.DifferingINIConfigOptions;
import constants.INIConfigNames;
import io.GameSettingINISaver;
import io.GameSettingsSaver;
import io.GeneratorSettingsLoader;
import validation.UserFolderValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSettingsGeneratorUI extends JFrame implements ActionListener {


    private String gameID;
    private String iniFilePath;
    private boolean isEditing = false;

    private ArrayList<JPanel> jPanels = new ArrayList<>();
    private ArrayList<JLabel> coreJLabels = new ArrayList<>();
    private ArrayList<JComboBox> coreJComboBoxes = new ArrayList<>();
    private ArrayList<JLabel> videoSettingsJLabels = new ArrayList<>();
    private ArrayList<JComboBox> videoSettingsJComboBoxes = new ArrayList<>();

    private ArrayList<JLabel> videoEnhancementsJLabels = new ArrayList<>();
    private ArrayList<JComboBox> videoEnhancementsJComboBoxes = new ArrayList<>();

    private ArrayList<JLabel> videoHacksJLabels = new ArrayList<>();
    private ArrayList<JComboBox> videoHacksJComboBoxes = new ArrayList<>();

    private JLabel videoHardwareLabel;
    private JComboBox videoHardwareJComboBox;

    private JLabel dspAudioLabel;
    private JSlider dspAudioVolumeSlider;

    private ArrayList<JLabel> wiiJLabels = new ArrayList<>();
    private ArrayList<JComboBox> wiiJComboBoxes = new ArrayList<>();

    private ArrayList<JLabel> controlJLabels = new ArrayList<>();
    private ArrayList<JComboBox> controlJComboBoxes = new ArrayList<>();
    private ArrayList<JTextField> controlJTextFields = new ArrayList<>();

    //if user folder exists, populate these instead of the JTextFields
    private ArrayList<JComboBox> controlProfileJComboBoxes = new ArrayList<>();

    private ArrayList<JButton> generateINIs = new ArrayList<>();

    //used only for editing existing INI files
    private ArrayList<String> otherLines = new ArrayList<>();
    private ArrayList<String> iniSettings = new ArrayList<>();
    private ArrayList<String> iniSettingsValues = new ArrayList<>();
    private ArrayList<String> coreOtherLines = new ArrayList<>();
    private ArrayList<String> videoSettingsOtherLines = new ArrayList<>();
    private ArrayList<String> videoEnhancementsOtherLines = new ArrayList<>();
    private ArrayList<String> videoHacksOtherLines = new ArrayList<>();
    private ArrayList<String> videoHardwareOtherLines = new ArrayList<>();
    private ArrayList<String> dspOtherLines = new ArrayList<>();
    private ArrayList<String> wiiOtherLines = new ArrayList<>();
    private ArrayList<String> controlsOtherLines = new ArrayList<>();
    private String[] supportedCategories = {"[Core]", "[Video_Settings]","[Video_Enhancements]", "[Video_Hacks]","[Video_Hardware]","[DSP]","[Wii]","[Controls]"};

    private boolean useUserFolder = false;
    private String userFolderPath = "";

    public GameSettingsGeneratorUI(String gameID, boolean isEditing, String iniFilePath)
    {
        this.gameID = gameID;
        this.isEditing = isEditing;
        this.iniFilePath = iniFilePath;

        this.useUserFolder = doesUserFolderExist();

        if (!isEditing) {
            setTitle("Game Settings INI Generator");
        }
        else {
            setTitle("Game Settings INI Editor");
        }

        generateUI();

        if (isEditing) {
            loadINISettingsToUI();
        }

        for (int i=0; i<jPanels.size(); i++) {
            String buttonText = "";
            if (!isEditing) {
                buttonText = "Generate Game Settings INI";
            }
            else {
                buttonText = "Save Edited Game Settings INI";
            }

            JButton jButton = new JButton(buttonText);
            generateINIs.add(jButton);
            generateINIs.get(i).addActionListener(this);
            jPanels.get(i).add(generateINIs.get(i));
        }
    }

    private boolean doesUserFolderExist() {
        GeneratorSettingsLoader generatorSettingsLoader = new GeneratorSettingsLoader();
        UserFolderValidator userFolderValidator = new UserFolderValidator();
        userFolderPath = generatorSettingsLoader.getAutoMovePath();

        File userFolder = new File(userFolderPath);

        return userFolder.exists() && userFolderValidator.isValidUserFolder(userFolderPath);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (checkIfButtonWasPressed(e)) {
            File tempSettingsFile = new File("settings.txt");

            GameSettingsSaver gameSettingsSaver = new GameSettingsSaver();

            if (!useUserFolder) {
                gameSettingsSaver.saveGameSettingsToTempFile(coreJComboBoxes, videoSettingsJComboBoxes, videoEnhancementsJComboBoxes, videoHacksJComboBoxes, videoHardwareJComboBox, dspAudioVolumeSlider, wiiJComboBoxes, controlJComboBoxes, controlJTextFields);
            }
            else {
                gameSettingsSaver.saveGameSettingsToTempFileWithUserFolder(coreJComboBoxes, videoSettingsJComboBoxes, videoEnhancementsJComboBoxes, videoHacksJComboBoxes, videoHardwareJComboBox, dspAudioVolumeSlider, wiiJComboBoxes, controlJComboBoxes, controlProfileJComboBoxes);
            }

            GameSettingINISaver gameSettingINISaver = new GameSettingINISaver(gameID);
            try {
                gameSettingINISaver.saveINI(isEditing, tempSettingsFile, iniFilePath, otherLines, coreOtherLines, videoSettingsOtherLines, videoEnhancementsOtherLines, videoHacksOtherLines, videoHardwareOtherLines, dspOtherLines, wiiOtherLines, controlsOtherLines);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (!tempSettingsFile.delete()) {
                System.out.println("Temp Settings File was not deleted");
            }

            if (!isEditing) {
                JOptionPane.showMessageDialog(this,  "Game Settings file has been successfully generated!");
            }
            else {
                JOptionPane.showMessageDialog(this,  "Game Settings file has been successfully edited!");
            }

            setVisible(false);
        }
    }

    private boolean checkIfButtonWasPressed(ActionEvent e) {
        return e.getSource() == generateINIs.get(0)
                || e.getSource() == generateINIs.get(1)
                || e.getSource() == generateINIs.get(2)
                || e.getSource() == generateINIs.get(3)
                || e.getSource() == generateINIs.get(4)
                || e.getSource() == generateINIs.get(5)
                || e.getSource() == generateINIs.get(6)
                || e.getSource() == generateINIs.get(7);
    }

    private void generateUI() {
        JPanel corePanel = new JPanel();
        GridLayout coreGridLayout = new GridLayout(ConfigNames.CORE_OPTIONS.length+1, 2);
        corePanel.setLayout(coreGridLayout);

        JPanel videoSettingsPanel = new JPanel();
        GridLayout videoSettingsGridLayout = new GridLayout(ConfigNames.VIDEO_SETTINGS_OPTIONS.length+1,2);
        videoSettingsPanel.setLayout(videoSettingsGridLayout);

        JPanel videoEnhancementsPanel = new JPanel();
        GridLayout videoEnhancementsGridLayout = new GridLayout(ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS.length+1,2);
        videoEnhancementsPanel.setLayout(videoEnhancementsGridLayout);

        JPanel videoHacksPanel = new JPanel();
        GridLayout videoHacksGridLayout = new GridLayout(ConfigNames.VIDEO_HACKS_OPTIONS.length+1,2);
        videoHacksPanel.setLayout(videoHacksGridLayout);

        JPanel videoHardwarePanel = new JPanel();
        GridLayout videoHardwareGridLayout = new GridLayout(ConfigNames.VIDEO_HARDWARE_OPTION.length+1,2);
        videoHardwarePanel.setLayout(videoHardwareGridLayout);

        JPanel dspPanel = new JPanel();
        GridLayout dspAudioGridLayout = new GridLayout(ConfigNames.DSP_AUDIO_OPTION.length+1,2);
        videoHardwarePanel.setLayout(dspAudioGridLayout);

        JPanel wiiPanel = new JPanel();
        GridLayout wiiGridLayout = new GridLayout(ConfigNames.WII_OPTIONS.length+1,2);
        wiiPanel.setLayout(wiiGridLayout);

        JPanel controlsPanel = new JPanel();
        GridLayout controlGridLayout = new GridLayout(ConfigNames.CONTROL_OPTIONS.length+1,2);
        controlsPanel.setLayout(controlGridLayout);

        addToCorePanel(corePanel);
        jPanels.add(corePanel);

        addToVideoSettingsPanel(videoSettingsPanel);
        jPanels.add(videoSettingsPanel);

        addToVideoEnhancementsPanel(videoEnhancementsPanel);
        jPanels.add(videoEnhancementsPanel);

        addToVideoHacksPanel(videoHacksPanel);
        jPanels.add(videoHacksPanel);

        addToVideoHardwarePanel(videoHardwarePanel);
        jPanels.add(videoHardwarePanel);

        addToDSPAudioPanel(dspPanel);
        jPanels.add(dspPanel);

        addToWiiPanel(wiiPanel);
        jPanels.add(wiiPanel);

        addToControlPanel(controlsPanel);
        jPanels.add(controlsPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Core", jPanels.get(0));
        tabbedPane.add("Video Settings", jPanels.get(1));
        tabbedPane.add("Video Enhancements", jPanels.get(2));
        tabbedPane.add("Video Hacks", jPanels.get(3));
        tabbedPane.add("Video Hardware", jPanels.get(4));
        tabbedPane.add("DSP Settings", jPanels.get(5));
        tabbedPane.add("Wii Settings", jPanels.get(6));
        tabbedPane.add("Control Settings", jPanels.get(7));
        add(tabbedPane);
    }

    private void addToCorePanel(JPanel corePanel) {

        for (int i = 0; i<ConfigNames.CORE_OPTIONS.length; i++) {
            coreJLabels.add(new JLabel(ConfigNames.CORE_OPTIONS[i]));
            corePanel.add(coreJLabels.get(i));
            coreJComboBoxes.add(new JComboBox(getAppropriateCoreOptions(ConfigNames.CORE_OPTIONS[i])));
            corePanel.add(coreJComboBoxes.get(i));
        }
    }

    private void addToVideoSettingsPanel(JPanel videoSettingsPanel) {

        for (int i = 0; i<ConfigNames.VIDEO_SETTINGS_OPTIONS.length; i++) {
            videoSettingsJLabels.add(new JLabel(ConfigNames.VIDEO_SETTINGS_OPTIONS[i]));
            videoSettingsPanel.add(videoSettingsJLabels.get(i));
            videoSettingsJComboBoxes.add(new JComboBox(getAppropriateVideoSettingsOptions(ConfigNames.VIDEO_SETTINGS_OPTIONS[i])));
            videoSettingsPanel.add(videoSettingsJComboBoxes.get(i));
        }
    }

    private void addToVideoEnhancementsPanel(JPanel videoEnhancementsPanel) {

        for (int i = 0; i<ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS.length; i++) {
            videoEnhancementsJLabels.add(new JLabel(ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS[i]));
            videoEnhancementsPanel.add(videoEnhancementsJLabels.get(i));
            videoEnhancementsJComboBoxes.add(new JComboBox(getAppropriateVideoEnhancementOptions(ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS[i])));
            videoEnhancementsPanel.add(videoEnhancementsJComboBoxes.get(i));
        }
    }
    private void addToVideoHacksPanel(JPanel videoHacksPanel) {

        for (int i = 0; i<ConfigNames.VIDEO_HACKS_OPTIONS.length; i++) {
            videoHacksJLabels.add(new JLabel(ConfigNames.VIDEO_HACKS_OPTIONS[i]));
            videoHacksPanel.add(videoHacksJLabels.get(i));
            videoHacksJComboBoxes.add(new JComboBox(ConfigOptions.TRUE_FALSE_OPTIONS));
            videoHacksPanel.add(videoHacksJComboBoxes.get(i));
        }
    }

    private void addToVideoHardwarePanel(JPanel videoHardwarePanel) {
        videoHardwareLabel = new JLabel(ConfigNames.VIDEO_HARDWARE_OPTION[0]);
        videoHardwarePanel.add(videoHardwareLabel);
        videoHardwareJComboBox = new JComboBox(ConfigOptions.TRUE_FALSE_OPTIONS);
        videoHardwarePanel.add(videoHardwareJComboBox);
    }

    private void addToDSPAudioPanel(JPanel dspAudioPanel) {
        dspAudioLabel = new JLabel(ConfigNames.DSP_AUDIO_OPTION[0]);
        dspAudioPanel.add(dspAudioLabel);
        dspAudioVolumeSlider = new JSlider(0, 100, 100);
        dspAudioVolumeSlider.setMajorTickSpacing(10);
        dspAudioVolumeSlider.setPaintTicks(true);
        dspAudioVolumeSlider.setPaintLabels(true);
        dspAudioPanel.add(dspAudioVolumeSlider);
    }

    private void addToWiiPanel(JPanel wiiPanel) {
        for (int i = 0; i<ConfigNames.WII_OPTIONS.length; i++) {
            wiiJLabels.add(new JLabel(ConfigNames.WII_OPTIONS[i]));
            wiiPanel.add(wiiJLabels.get(i));
            wiiJComboBoxes.add(new JComboBox(getAppropriateWiiOptions(ConfigNames.WII_OPTIONS[i])));
            wiiPanel.add(wiiJComboBoxes.get(i));
        }
    }

    private void addToControlPanel(JPanel controlPanel) {

        int textFieldIndex = 0;
        int jComboBoxesAdded = 0;
        int jComboProfileBoxesAdded = 0;

        //use WiiTDB file to get folder path
        File tempSettingsFile = new File("wiitdb.txt");
        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);


        for (int i = 0; i<ConfigNames.CONTROL_OPTIONS.length; i++) {
            controlJLabels.add(new JLabel(ConfigNames.CONTROL_OPTIONS[i]));
            controlPanel.add(controlJLabels.get(i));
            if (!isProfileOption(ConfigNames.CONTROL_OPTIONS[i])) {
                controlJComboBoxes.add(new JComboBox(getAppropriateControlOptions(ConfigNames.CONTROL_OPTIONS[i])));
                controlPanel.add(controlJComboBoxes.get(jComboBoxesAdded));
                jComboBoxesAdded++;
            }
            else {

                if (!useUserFolder) {
                    JTextField controlJTextField = new JTextField();
                    controlJTextFields.add(controlJTextField);
                    controlPanel.add(controlJTextFields.get(textFieldIndex));
                    textFieldIndex++;
                }
                else {

                    String profileFilePath = userFolderPath + filePathSeparator + "Config" + filePathSeparator + "Profiles";
                    File gcProfiles = new File(profileFilePath + filePathSeparator + "GCPad");
                    File wiiProfiles = new File(profileFilePath + filePathSeparator + "Wiimote");
                    JComboBox gcComboBox = new JComboBox(getFileNames(gcProfiles));
                    JComboBox wiiComboBox = new JComboBox(getFileNames(wiiProfiles));

                    //4 is there because number of controller ports
                    if (jComboProfileBoxesAdded/4 == 0) {
                        controlProfileJComboBoxes.add(gcComboBox);
                        controlPanel.add(controlProfileJComboBoxes.get(jComboProfileBoxesAdded));
                    }
                    else {
                        controlProfileJComboBoxes.add(wiiComboBox);
                        controlPanel.add(controlProfileJComboBoxes.get(jComboProfileBoxesAdded));
                    }

                    jComboProfileBoxesAdded++;
                }
            }
        }
    }

    private String[] getFileNames(File folder) {
        File[] folderFiles = folder.listFiles();

        //in the event the folder doesn't exist, just return an array with "None" in it
        if (folderFiles == null) {
            String[] nullArr = new String[1];
            nullArr[0] = "None";
            return nullArr;
        }

        String[] folderFileNames = new String[folderFiles.length + 1];
        folderFileNames[0] = "None";
        for (int i=0; i<folderFiles.length; i++) {
            folderFileNames[i+1] = folderFiles[i].getName();
            folderFileNames[i+1] = folderFileNames[i+1].substring(0, folderFileNames[i+1].lastIndexOf("."));
        }

        return folderFileNames;
    }

    private String[] getAppropriateCoreOptions(String coreOption) {
        if (coreOption.equals("Graphics Backend")) {
            return ConfigOptions.GRAPHIC_BACKENDS;
        }
        else if (coreOption.equals("CPU Emulator Engine")) {
            return ConfigOptions.CPU_EMULATOR_ENGINE;
        }
        else if (coreOption.equals("Emulation Speed")) {
            return ConfigOptions.EMULATION_SPEEDS;
        }
        else if (coreOption.equals("CPU Overclock") || coreOption.equals("GPU Overclock")) {
            return ConfigOptions.OVER_CLOCK_SPEEDS;
        }
        else if (coreOption.equals("GameCube Language")) {
            return ConfigOptions.GAMECUBE_LANGUAGE;
        }
        else if (coreOption.equals("GameCube Slot A") || coreOption.equals("GameCube Slot B")) {
            return ConfigOptions.GAMECUBE_SLOT;
        }
        else if (coreOption.equals("GameCube Serial Port")) {
            return ConfigOptions.GAMECUBE_SERIAL_PORT;
        }
        else {
            return ConfigOptions.TRUE_FALSE_OPTIONS;
        }
    }

    private String[] getAppropriateVideoSettingsOptions(String videoSettingOption) {
        if (videoSettingOption.equals("Internal Resolution")) {
            return ConfigOptions.INTERNAL_RESOLUTIONS;
        }
        else if (videoSettingOption.equals("Shader Compilation")) {
            return ConfigOptions.SHADER_COMPILATION_METHODS;
        }
        else if (videoSettingOption.equals("Texture Cache Accuracy")) {
            return ConfigOptions.TEXTURE_CACHE_ACCURACIES;
        }
        else if (videoSettingOption.equals("Aspect Ratio")) {
            return ConfigOptions.ASPECT_RATIOS;
        }
        else if (videoSettingOption.equals("Anti-Aliasing")) {
            return ConfigOptions.ANTI_ALIASING;
        }
        else if (videoSettingOption.equals("Anti-Aliasing Method")) {
            return ConfigOptions.ANTI_ALIASING_METHODS;
        }
        else {
            return ConfigOptions.TRUE_FALSE_OPTIONS;
        }
    }

    private String[] getAppropriateVideoEnhancementOptions(String videoEnhancementOption) {
        if (videoEnhancementOption.equals("Anisotropic Filtering")) {
            return ConfigOptions.ANISOTROPIC_FILTERING;
        }
        else if (videoEnhancementOption.equals("Force Texture Filtering")) {
            return ConfigOptions.TEXTURE_FILTERING;
        }
        else {
            return ConfigOptions.TRUE_FALSE_OPTIONS;
        }
    }

    private String[] getAppropriateWiiOptions(String wiiOption) {
        if (wiiOption.equals("Wii Language")) {
            return ConfigOptions.WII_LANGUAGE;
        }
        else if (wiiOption.equals("Wii Aspect Ratio")) {
            return ConfigOptions.WII_ASPECT_RATIO;
        }
        else {
            return ConfigOptions.TRUE_FALSE_OPTIONS;
        }
    }

    private String[] getAppropriateControlOptions(String controlOption) {
        if (controlOption.equals("GameCube Controller Port 1")
                || controlOption.equals("GameCube Controller Port 2")
                || controlOption.equals("GameCube Controller Port 3")
                || controlOption.equals("GameCube Controller Port 4")) {
            return ConfigOptions.GAMECUBE_CONTROLLER_TYPES;
        }
        else if (controlOption.equals("Wii Remote 1")
                || controlOption.equals("Wii Remote 2")
                || controlOption.equals("Wii Remote 3")
                || controlOption.equals("Wii Remote 4")) {
            return ConfigOptions.WII_CONTROLLER_TYPES;
        }
        else {
            return ConfigOptions.TRUE_FALSE_OPTIONS;
        }
    }

    private boolean isProfileOption(String controlOption) {
        return controlOption.equals("GameCube Controller Port 1 Profile")
                || controlOption.equals("GameCube Controller Port 2 Profile")
                || controlOption.equals("GameCube Controller Port 3 Profile")
                || controlOption.equals("GameCube Controller Port 4 Profile")
                || controlOption.equals("Wii Remote 1 Profile")
                || controlOption.equals("Wii Remote 2 Profile")
                || controlOption.equals("Wii Remote 3 Profile")
                || controlOption.equals("Wii Remote 4 Profile");
    }

    private void loadINISettingsToUI() {
        //use WiiTDB file to get folder path
        File tempSettingsFile = new File("wiitdb.txt");
        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);
        String wiiTDBFilePathFolder = wiiTDBFilePath.substring(0, wiiTDBFilePath.lastIndexOf(filePathSeparator));

        File chosenINIFile = new File(wiiTDBFilePathFolder + filePathSeparator + "GameSettings" + filePathSeparator + gameID + ".ini");

        ArrayList<String> iniLines;

        if (gameID != null) {
            iniLines = readINILines(chosenINIFile);
        }
        else {
            iniLines = readINILines(new File(iniFilePath));
        }

        getINISettings(iniLines);
        ArrayList<String> translatedINISettings = translateINISettings(iniSettings);
        loadIntoUI(translatedINISettings, iniSettingsValues);
    }

    private void loadIntoUI(ArrayList<String> translatedINISettings, ArrayList<String> iniSettingValues) {
        for (int i=0; i<translatedINISettings.size(); i++) {
            setAppropriateUIElement(translatedINISettings.get(i), iniSettingValues.get(i).trim());
        }
    }

    private void setAppropriateUIElement(String translatedINISetting, String settingValue) {

        for (int i=0; i<coreJLabels.size(); i++) {
            setUIElement(coreJLabels, coreJComboBoxes, translatedINISetting, settingValue, i);
        }

        for (int i=0; i<videoSettingsJLabels.size(); i++) {
            setUIElement(videoSettingsJLabels, videoSettingsJComboBoxes, translatedINISetting, settingValue, i);
        }

        for (int i=0; i<videoEnhancementsJLabels.size(); i++) {
            setUIElement(videoEnhancementsJLabels, videoEnhancementsJComboBoxes, translatedINISetting, settingValue, i);
        }

        for (int i=0; i<videoHacksJLabels.size(); i++) {
            setUIElement(videoHacksJLabels, videoHacksJComboBoxes, translatedINISetting, settingValue, i);
        }

        if (translatedINISetting.equals(videoHardwareLabel.getText())) {
            if (settingValue.equals("True")) {
                videoHardwareJComboBox.setSelectedIndex(1);
            }
            else {
                videoHardwareJComboBox.setSelectedIndex(2);
            }
        }

        if (translatedINISetting.equals(dspAudioLabel.getText())) {
            dspAudioVolumeSlider.setValue(Integer.parseInt(settingValue));
        }

        for (int i=0; i<wiiJLabels.size(); i++) {
            setUIElement(wiiJLabels, wiiJComboBoxes, translatedINISetting, settingValue, i);
        }

        int comboBoxIndex = 0;
        int textFieldIndex = 0;
        setControlsUIElements(translatedINISetting, settingValue, comboBoxIndex, textFieldIndex);
    }

    private void setControlsUIElements(String translatedINISetting, String settingValue, int comboBoxIndex, int textFieldIndex) {
        for (int i=0; i<controlJLabels.size(); i++) {

            if (translatedINISetting.equals(controlJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.DIFFERING_INI_CONFIG_OPTIONS[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    controlJComboBoxes.get(comboBoxIndex).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (!useUserFolder) {
                        controlJTextFields.get(textFieldIndex).setText(settingValue);
                    }
                    else {
                        File tempSettingsFile = new File("wiitdb.txt");
                        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
                        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
                        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);

                        String profileFilePath = userFolderPath + filePathSeparator + "Config" + filePathSeparator + "Profiles";
                        File gcProfiles = new File(profileFilePath + filePathSeparator + "GCPad");
                        File wiiProfiles = new File(profileFilePath + filePathSeparator + "Wiimote");
                        String[] gcProfileStrings = getFileNames(gcProfiles);
                        String[] wiiProfileStrings = getFileNames(wiiProfiles);

                        //4 is there because number of controller ports
                        if (textFieldIndex/4 == 0) {
                            controlProfileJComboBoxes.get(textFieldIndex).setSelectedIndex(getControlProfileJComboBoxIndex(gcProfileStrings, settingValue));
                        }
                        else {
                            controlProfileJComboBoxes.get(textFieldIndex).setSelectedIndex(getControlProfileJComboBoxIndex(wiiProfileStrings, settingValue));
                        }
                    }
                }
            }

            if (controlJLabels.get(i).getText().contains("Profile")) {
                textFieldIndex++;
            }
            else {
                comboBoxIndex++;
            }
        }
    }

    private int getControlProfileJComboBoxIndex(String[] profileStrings, String settingValue) {
        int index = 0;
        for (int i=1; i<profileStrings.length; i++) {
            if (settingValue.equals(profileStrings[i])) {
                return i;
            }
        }

        return index;
    }

    private void setUIElement(ArrayList<JLabel> jLabels, ArrayList<JComboBox> comboBoxes, String translatedINISetting, String settingValue, int i) {
        if (translatedINISetting.equals(jLabels.get(i).getText())) {
            if (isInArray(translatedINISetting, DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES)) {
                int index = getIndexOfDifferingSetting(translatedINISetting);
                String[] iniConfigOptions = DifferingINIConfigOptions.DIFFERING_INI_CONFIG_OPTIONS[index];
                int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                comboBoxes.get(i).setSelectedIndex(indexToSetTo);
            }
            else {
                if (settingValue.equals("True")) {
                    comboBoxes.get(i).setSelectedIndex(1);
                }
                else {
                    comboBoxes.get(i).setSelectedIndex(2);
                }
            }
        }
    }


    private int getIndexToSetTo(String settingValue, String[] iniConfigOptions) {
        for (int i=0; i<iniConfigOptions.length; i++) {
            if (iniConfigOptions[i].equals(settingValue)) {
                return i;
            }
        }

        return 0;
    }

    private boolean isInArray(String string, String[] array) {
        for (int i=0; i<array.length; i++) {
            if (string.equals(array[i])) {
                return true;
            }
        }

        return false;
    }

    private int getIndexOfDifferingSetting(String differingSetting) {
        for (int i = 0; i<DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES.length; i++) {
            if (differingSetting.equals(DifferingINIConfigOptions.DIFFERENT_CONFIG_OPTION_NAMES[i])) {
                return i;
            }
        }

        return 0;
    }

    private ArrayList<String> readINILines(File chosenINIFile) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(chosenINIFile));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        while (inputStream.hasNextLine()) {
            lines.add(inputStream.nextLine());
        }

        inputStream.close();
        return lines;
    }

    private void getINISettings(ArrayList<String> iniLines) {
       boolean isValidBlock = false;
       String blockName = "";

        for (int i=0; i<iniLines.size(); i++) {
            String iniLine = iniLines.get(i);
            if (!iniLine.contains("[") && !iniLine.contains("OverclockEnable") && isValidBlock) {
                String iniSettingName = iniLine.split("=")[0];
                String iniSettingValue = iniLine.split("=")[1];
                if (isSettingSupported(blockName, iniSettingName.trim())) {
                    iniSettings.add(iniSettingName);
                    iniSettingsValues.add(iniSettingValue);
                }
                else {
                    addToBlockOtherLines(blockName, iniLine);
                }
            }
            else if (iniLine.contains("[")) {
                isValidBlock = false;

                for (int index=0; index<supportedCategories.length; index++) {
                    if (iniLine.equals(supportedCategories[index])) {
                        isValidBlock = true;
                        blockName = supportedCategories[index];
                        break;
                    }
                }

                if (!isValidBlock) {
                    otherLines.add(iniLine);
                }
            }
            else {
                otherLines.add(iniLine);
            }
        }
    }

    private boolean isSettingSupported(String blockName, String settingName) {

        if (blockName.equals("[Core]")) {
            return isInArray(settingName, INIConfigNames.INI_CORE_OPTIONS);
        }
        else if (blockName.equals("[Video_Settings]")) {
            return isInArray(settingName, INIConfigNames.INI_VIDEO_SETTINGS_OPTIONS);
        }
        else if (blockName.equals("[Video_Enhancements]")) {
            return isInArray(settingName, INIConfigNames.INI_VIDEO_ENHANCEMENTS_OPTIONS);
        }
        else if (blockName.equals("[Video_Hacks]")) {
            return isInArray(settingName, INIConfigNames.INI_VIDEO_HACKS_OPTIONS);
        }
        else if (blockName.equals("[Video_Hardware]")) {
            return isInArray(settingName, INIConfigNames.INI_VIDEO_HARDWARE_OPTION);
        }
        else if (blockName.equals("[DSP]")) {
            return isInArray(settingName, INIConfigNames.INI_DSP_AUDIO_OPTION);
        }
        else if (blockName.equals("[Wii]")) {
            return isInArray(settingName, INIConfigNames.INI_WII_OPTIONS);
        }
        else if (blockName.equals("[Controls]")) {
            return isInArray(settingName, INIConfigNames.INI_CONTROL_OPTIONS);
        }

        return false;
    }

    private void addToBlockOtherLines(String blockName, String iniLine) {

        if (blockName.equals("[Core]")) {
            coreOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Video_Settings]")) {
            videoSettingsOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Video_Enhancements]")) {
            videoEnhancementsOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Video_Hacks]")) {
            videoHacksOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Video_Hardware]")) {
            videoHardwareOtherLines.add(iniLine);
        }
        else if (blockName.equals("[DSP]")) {
            dspOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Wii]")) {
            wiiOtherLines.add(iniLine);
        }
        else if (blockName.equals("[Controls]")) {
            controlsOtherLines.add(iniLine);
        }
    }

    private ArrayList<String> translateINISettings(ArrayList<String> iniSettings) {
        ArrayList<String> translatedIniSettings = new ArrayList<>();

        for (int i=0; i<iniSettings.size(); i++) {
            String iniSetting = iniSettings.get(i).trim();
            String translatedINISetting = getTranslatedSetting(iniSetting);
            translatedIniSettings.add(translatedINISetting);
        }

        return translatedIniSettings;
    }

    private String getTranslatedSetting(String iniSetting) {

        for (int i = 0; i<INIConfigNames.INI_CORE_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_CORE_OPTIONS[i])) {
                return ConfigNames.CORE_OPTIONS[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_VIDEO_SETTINGS_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_VIDEO_SETTINGS_OPTIONS[i])) {
                return ConfigNames.VIDEO_SETTINGS_OPTIONS[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_VIDEO_ENHANCEMENTS_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_VIDEO_ENHANCEMENTS_OPTIONS[i])) {
                return ConfigNames.VIDEO_ENHANCEMENTS_OPTIONS[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_VIDEO_HACKS_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_VIDEO_HACKS_OPTIONS[i])) {
                return ConfigNames.VIDEO_HACKS_OPTIONS[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_VIDEO_HARDWARE_OPTION.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_VIDEO_HARDWARE_OPTION[i])) {
                return ConfigNames.VIDEO_HARDWARE_OPTION[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_DSP_AUDIO_OPTION.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_DSP_AUDIO_OPTION[i])) {
                return ConfigNames.DSP_AUDIO_OPTION[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_WII_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_WII_OPTIONS[i])) {
                return ConfigNames.WII_OPTIONS[i];
            }
        }

        for (int i = 0; i<INIConfigNames.INI_CONTROL_OPTIONS.length; i++) {
            if (iniSetting.equals(INIConfigNames.INI_CONTROL_OPTIONS[i])) {
                return ConfigNames.CONTROL_OPTIONS[i];
            }
        }

        return "";
    }
}