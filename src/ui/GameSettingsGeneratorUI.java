package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import constants.*;
import io.GameSettingINISaver;
import io.GameSettingsSaver;

public class GameSettingsGeneratorUI extends JFrame implements ActionListener {


    private String gameID;
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

    private ArrayList<JButton> generateINIs = new ArrayList<>();



    public GameSettingsGeneratorUI(String gameID, boolean isEditing)
    {
        this.gameID = gameID;
        this.isEditing = isEditing;

        setTitle("Game Settings INI Generator");
        generateUI();

        if (isEditing) {
            loadINISettingsToUI();
        }

        for (int i=0; i<jPanels.size(); i++) {
            JButton jButton = new JButton("Generate Game Settings INI");
            generateINIs.add(jButton);
            generateINIs.get(i).addActionListener(this);
            jPanels.get(i).add(generateINIs.get(i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (checkIfButtonWasPressed(e)) {
            File tempSettingsFile = new File("settings.txt");

            GameSettingsSaver gameSettingsSaver = new GameSettingsSaver();
            gameSettingsSaver.saveGameSettingsToTempFile(coreJComboBoxes, videoSettingsJComboBoxes, videoEnhancementsJComboBoxes, videoHacksJComboBoxes, videoHardwareJComboBox, dspAudioVolumeSlider, wiiJComboBoxes, controlJComboBoxes, controlJTextFields);
            GameSettingINISaver gameSettingINISaver = new GameSettingINISaver(gameID);
            gameSettingINISaver.saveINI(tempSettingsFile);

            if (!tempSettingsFile.delete()) {
                System.out.println("Temp Settings File was not deleted");
            }
            JOptionPane.showMessageDialog(this,  "Game Settings file has been successfully generated!");
            setVisible(false);
            DolphinINIGeneratorUI dolphinINIGeneratorUI = new DolphinINIGeneratorUI();
            dolphinINIGeneratorUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dolphinINIGeneratorUI.pack();
            dolphinINIGeneratorUI.setVisible(true);
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
        GridLayout coreGridLayout = new GridLayout(ConfigNames.coreOptions.length+1, 2);
        corePanel.setLayout(coreGridLayout);

        JPanel videoSettingsPanel = new JPanel();
        GridLayout videoSettingsGridLayout = new GridLayout(ConfigNames.videoSettingsOptions.length+1,2);
        videoSettingsPanel.setLayout(videoSettingsGridLayout);

        JPanel videoEnhancementsPanel = new JPanel();
        GridLayout videoEnhancementsGridLayout = new GridLayout(ConfigNames.videoEnhancementsOptions.length+1,2);
        videoEnhancementsPanel.setLayout(videoEnhancementsGridLayout);

        JPanel videoHacksPanel = new JPanel();
        GridLayout videoHacksGridLayout = new GridLayout(ConfigNames.videoHacksOptions.length+1,2);
        videoHacksPanel.setLayout(videoHacksGridLayout);

        JPanel videoHardwarePanel = new JPanel();
        GridLayout videoHardwareGridLayout = new GridLayout(ConfigNames.videoHardwareOption.length+1,2);
        videoHardwarePanel.setLayout(videoHardwareGridLayout);

        JPanel dspPanel = new JPanel();
        GridLayout dspAudioGridLayout = new GridLayout(ConfigNames.dspAudioOption.length+1,2);
        videoHardwarePanel.setLayout(dspAudioGridLayout);

        JPanel wiiPanel = new JPanel();
        GridLayout wiiGridLayout = new GridLayout(ConfigNames.wiiOptions.length+1,2);
        wiiPanel.setLayout(wiiGridLayout);

        JPanel controlsPanel = new JPanel();
        GridLayout controlGridLayout = new GridLayout(ConfigNames.controlOptions.length+1,2);
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

        for (int i=0; i<ConfigNames.coreOptions.length; i++) {
            coreJLabels.add(new JLabel(ConfigNames.coreOptions[i]));
            corePanel.add(coreJLabels.get(i));
            coreJComboBoxes.add(new JComboBox(getAppropriateCoreOptions(ConfigNames.coreOptions[i])));
            corePanel.add(coreJComboBoxes.get(i));
        }
    }

    private void addToVideoSettingsPanel(JPanel videoSettingsPanel) {

        for (int i=0; i<ConfigNames.videoSettingsOptions.length; i++) {
            videoSettingsJLabels.add(new JLabel(ConfigNames.videoSettingsOptions[i]));
            videoSettingsPanel.add(videoSettingsJLabels.get(i));
            videoSettingsJComboBoxes.add(new JComboBox(getAppropriateVideoSettingsOptions(ConfigNames.videoSettingsOptions[i])));
            videoSettingsPanel.add(videoSettingsJComboBoxes.get(i));
        }
    }

    private void addToVideoEnhancementsPanel(JPanel videoEnhancementsPanel) {

        for (int i=0; i<ConfigNames.videoEnhancementsOptions.length; i++) {
            videoEnhancementsJLabels.add(new JLabel(ConfigNames.videoEnhancementsOptions[i]));
            videoEnhancementsPanel.add(videoEnhancementsJLabels.get(i));
            videoEnhancementsJComboBoxes.add(new JComboBox(getAppropriateVideoEnhancementOptions(ConfigNames.videoEnhancementsOptions[i])));
            videoEnhancementsPanel.add(videoEnhancementsJComboBoxes.get(i));
        }
    }
    private void addToVideoHacksPanel(JPanel videoHacksPanel) {

        for (int i=0; i<ConfigNames.videoHacksOptions.length; i++) {
            videoHacksJLabels.add(new JLabel(ConfigNames.videoHacksOptions[i]));
            videoHacksPanel.add(videoHacksJLabels.get(i));
            videoHacksJComboBoxes.add(new JComboBox(ConfigOptions.trueFalseOptions));
            videoHacksPanel.add(videoHacksJComboBoxes.get(i));
        }
    }

    private void addToVideoHardwarePanel(JPanel videoHardwarePanel) {
        videoHardwareLabel = new JLabel(ConfigNames.videoHardwareOption[0]);
        videoHardwarePanel.add(videoHardwareLabel);
        videoHardwareJComboBox = new JComboBox(ConfigOptions.trueFalseOptions);
        videoHardwarePanel.add(videoHardwareJComboBox);
    }

    private void addToDSPAudioPanel(JPanel dspAudioPanel) {
        dspAudioLabel = new JLabel(ConfigNames.dspAudioOption[0]);
        dspAudioPanel.add(dspAudioLabel);
        dspAudioVolumeSlider = new JSlider(0, 100, 100);
        dspAudioVolumeSlider.setMajorTickSpacing(10);
        dspAudioVolumeSlider.setPaintTicks(true);
        dspAudioVolumeSlider.setPaintLabels(true);
        dspAudioPanel.add(dspAudioVolumeSlider);
    }

    private void addToWiiPanel(JPanel wiiPanel) {
        for (int i=0; i<ConfigNames.wiiOptions.length; i++) {
            wiiJLabels.add(new JLabel(ConfigNames.wiiOptions[i]));
            wiiPanel.add(wiiJLabels.get(i));
            wiiJComboBoxes.add(new JComboBox(getAppropriateWiiOptions(ConfigNames.wiiOptions[i])));
            wiiPanel.add(wiiJComboBoxes.get(i));
        }
    }

    private void addToControlPanel(JPanel controlPanel) {

        int textFieldIndex = 0;
        int jComboBoxesAdded = 0;

        for (int i=0; i<ConfigNames.controlOptions.length; i++) {
            controlJLabels.add(new JLabel(ConfigNames.controlOptions[i]));
            controlPanel.add(controlJLabels.get(i));
            if (!isProfileOption(ConfigNames.controlOptions[i])) {
                controlJComboBoxes.add(new JComboBox(getAppropriateControlOptions(ConfigNames.controlOptions[i])));
                controlPanel.add(controlJComboBoxes.get(jComboBoxesAdded));
                jComboBoxesAdded++;
            }
            else {
                JTextField controlJTextField = new JTextField();
                controlJTextFields.add(controlJTextField);
                controlPanel.add(controlJTextFields.get(textFieldIndex));
                textFieldIndex++;
            }
        }
    }



    private String[] getAppropriateCoreOptions(String coreOption) {
        if (coreOption.equals("Graphics Backend")) {
            return ConfigOptions.graphicBackends;
        }
        else if (coreOption.equals("CPU Emulator Engine")) {
            return ConfigOptions.cpuEmulatorEngine;
        }
        else if (coreOption.equals("Emulation Speed")) {
            return ConfigOptions.emulationSpeeds;
        }
        else if (coreOption.equals("CPU Overclock") || coreOption.equals("GPU Overclock")) {
            return ConfigOptions.overClockSpeeds;
        }
        else if (coreOption.equals("GameCube Language")) {
            return ConfigOptions.gameCubeLanguage;
        }
        else if (coreOption.equals("GameCube Slot A") || coreOption.equals("GameCube Slot B")) {
            return ConfigOptions.gameCubeSlot;
        }
        else if (coreOption.equals("GameCube Serial Port")) {
            return ConfigOptions.gameCubeSerialPort;
        }
        else {
            return ConfigOptions.trueFalseOptions;
        }
    }

    private String[] getAppropriateVideoSettingsOptions(String videoSettingOption) {
        if (videoSettingOption.equals("Internal Resolution")) {
            return ConfigOptions.internalResolutions;
        }
        else if (videoSettingOption.equals("Shader Compilation")) {
            return ConfigOptions.shaderCompilationMethods;
        }
        else if (videoSettingOption.equals("Texture Cache Accuracy")) {
            return ConfigOptions.textureCacheAccuracies;
        }
        else if (videoSettingOption.equals("Aspect Ratio")) {
            return ConfigOptions.aspectRatios;
        }
        else if (videoSettingOption.equals("Anti-Aliasing")) {
            return ConfigOptions.antiAliasing;
        }
        else if (videoSettingOption.equals("Anti-Aliasing Method")) {
            return ConfigOptions.antiAliasingMethods;
        }
        else {
            return ConfigOptions.trueFalseOptions;
        }
    }

    private String[] getAppropriateVideoEnhancementOptions(String videoEnhancementOption) {
        if (videoEnhancementOption.equals("Anisotropic Filtering")) {
            return ConfigOptions.anisotropicFiltering;
        }
        else if (videoEnhancementOption.equals("Force Texture Filtering")) {
            return ConfigOptions.textureFiltering;
        }
        else {
            return ConfigOptions.trueFalseOptions;
        }
    }

    private String[] getAppropriateWiiOptions(String wiiOption) {
        if (wiiOption.equals("Wii Language")) {
            return ConfigOptions.wiiLanguage;
        }
        else if (wiiOption.equals("Wii Aspect Ratio")) {
            return ConfigOptions.wiiAspectRatio;
        }
        else {
            return ConfigOptions.trueFalseOptions;
        }
    }

    private String[] getAppropriateControlOptions(String controlOption) {
        if (controlOption.equals("GameCube Controller Port 1")
                || controlOption.equals("GameCube Controller Port 2")
                || controlOption.equals("GameCube Controller Port 3")
                || controlOption.equals("GameCube Controller Port 4")) {
            return ConfigOptions.gameCubeControllerTypes;
        }
        else if (controlOption.equals("Wii Remote 1")
                || controlOption.equals("Wii Remote 2")
                || controlOption.equals("Wii Remote 3")
                || controlOption.equals("Wii Remote 4")) {
            return ConfigOptions.wiiControllerTypes;
        }
        else {
            return ConfigOptions.trueFalseOptions;
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
        ArrayList<String> iniLines = readINILines(chosenINIFile);
        ArrayList<String> iniSettings = getINISettings(iniLines);
        ArrayList<String> iniSettingValues = getINIValues(iniLines);
        ArrayList<String> translatedINISettings = translateINISettings(iniSettings);
        loadIntoUI(translatedINISettings, iniSettingValues);
    }

    private void loadIntoUI(ArrayList<String> translatedINISettings, ArrayList<String> iniSettingValues) {
        for (int i=0; i<translatedINISettings.size(); i++) {
            setAppropriateUIElement(translatedINISettings.get(i), iniSettingValues.get(i));
        }
    }

    private void setAppropriateUIElement(String translatedINISetting, String settingValue) {


        for (int i=0; i<coreJLabels.size(); i++) {
            if (translatedINISetting.equals(coreJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    coreJComboBoxes.get(i).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (settingValue.equals("True")) {
                        coreJComboBoxes.get(i).setSelectedIndex(1);
                    }
                    else {
                        coreJComboBoxes.get(i).setSelectedIndex(2);
                    }
                }
            }
        }

        for (int i=0; i<videoSettingsJLabels.size(); i++) {
            if (translatedINISetting.equals(videoSettingsJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    videoSettingsJComboBoxes.get(i).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (settingValue.equals("True")) {
                        videoSettingsJComboBoxes.get(i).setSelectedIndex(1);
                    }
                    else {
                        videoSettingsJComboBoxes.get(i).setSelectedIndex(2);
                    }
                }
            }
        }

        for (int i=0; i<videoEnhancementsJLabels.size(); i++) {
            if (translatedINISetting.equals(videoEnhancementsJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    videoEnhancementsJComboBoxes.get(i).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (settingValue.equals("True")) {
                        videoEnhancementsJComboBoxes.get(i).setSelectedIndex(1);
                    }
                    else {
                        videoEnhancementsJComboBoxes.get(i).setSelectedIndex(2);
                    }
                }
            }
        }

        for (int i=0; i<videoHacksJLabels.size(); i++) {
            if (translatedINISetting.equals(videoHacksJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    videoHacksJComboBoxes.get(i).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (settingValue.equals("True")) {
                        videoHacksJComboBoxes.get(i).setSelectedIndex(1);
                    }
                    else {
                        videoHacksJComboBoxes.get(i).setSelectedIndex(2);
                    }
                }
            }
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
            if (translatedINISetting.equals(wiiJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    wiiJComboBoxes.get(i).setSelectedIndex(indexToSetTo);
                }
                else {
                    if (settingValue.equals("True")) {
                        wiiJComboBoxes.get(i).setSelectedIndex(1);
                    }
                    else {
                        wiiJComboBoxes.get(i).setSelectedIndex(2);
                    }
                }
            }
        }

        int comboBoxIndex = 0;
        int textFieldIndex = 0;

        for (int i=0; i<controlJLabels.size(); i++) {

            if (translatedINISetting.equals(controlJLabels.get(i).getText())) {
                if (isInArray(translatedINISetting, DifferingINIConfigOptions.differentConfigOptionNames)) {
                    int index = getIndexOfDifferingSetting(translatedINISetting);
                    String[] iniConfigOptions = DifferingINIConfigOptions.differingINIConfigOptions[index];
                    int indexToSetTo = getIndexToSetTo(settingValue, iniConfigOptions);
                    controlJComboBoxes.get(comboBoxIndex).setSelectedIndex(indexToSetTo);
                }
                else {
                    controlJTextFields.get(textFieldIndex).setText(settingValue);
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
        for (int i=0; i<DifferingINIConfigOptions.differentConfigOptionNames.length; i++) {
            if (differingSetting.equals(DifferingINIConfigOptions.differentConfigOptionNames[i])) {
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

    private ArrayList<String> getINISettings(ArrayList<String> iniLines) {
        ArrayList<String> iniSettings = new ArrayList<>();

        for (int i=0; i<iniLines.size(); i++) {
            String iniLine = iniLines.get(i);
            if (!iniLine.contains("[") && !iniLine.contains("OverclockEnable")) {
                String iniSettingName = iniLine.split("=")[0];
                iniSettings.add(iniSettingName);
            }
        }

        return iniSettings;
    }

    private ArrayList<String> getINIValues(ArrayList<String> iniLines) {
        ArrayList<String> iniSettingsValues = new ArrayList<>();

        for (int i=0; i<iniLines.size(); i++) {
            String iniLine = iniLines.get(i);
            if (!iniLine.contains("[") && !iniLine.contains("OverclockEnable")) {
                String iniSettingValue = iniLine.split("=")[1];
                iniSettingsValues.add(iniSettingValue);
            }
        }

        return iniSettingsValues;
    }

    private ArrayList<String> translateINISettings(ArrayList<String> iniSettings) {
        ArrayList<String> translatedIniSettings = new ArrayList<>();

        for (int i=0; i<iniSettings.size(); i++) {
            String iniSetting = iniSettings.get(i);
            String translatedINISetting = getTranslatedSetting(iniSetting);
            translatedIniSettings.add(translatedINISetting);
        }

        return translatedIniSettings;
    }

    private String getTranslatedSetting(String iniSetting) {

        for (int i=0; i<INIConfigNames.INICoreOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INICoreOptions[i])) {
                return ConfigNames.coreOptions[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIVideoSettingsOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIVideoSettingsOptions[i])) {
                return ConfigNames.videoSettingsOptions[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIVideoEnhancementsOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIVideoEnhancementsOptions[i])) {
                return ConfigNames.videoEnhancementsOptions[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIVideoHacksOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIVideoHacksOptions[i])) {
                return ConfigNames.videoHacksOptions[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIVideoHardwareOption.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIVideoHardwareOption[i])) {
                return ConfigNames.videoHardwareOption[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIDspAudioOption.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIDspAudioOption[i])) {
                return ConfigNames.dspAudioOption[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIWiiOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIWiiOptions[i])) {
                return ConfigNames.wiiOptions[i];
            }
        }

        for (int i=0; i<INIConfigNames.INIControlOptions.length; i++) {
            if (iniSetting.equals(INIConfigNames.INIControlOptions[i])) {
                return ConfigNames.controlOptions[i];
            }
        }

        return "";
    }
}