package ui;

import constants.GeneratorSettings;
import io.GameSettingsMover;
import validation.UserFolderValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DolphinINIGeneratorUI extends JFrame implements ActionListener {


    private JButton pickGame, editINI, moveGameSettingsToUserFolder, browseForAutoSyncUserFolderPath;
    private JLabel useCoverArtLabel, autoSyncEnabledLabel, autoSyncUserFolderPathLabel;
    private JCheckBox useCoverArt, autoSyncEnabled;
    private String userFolderPath = "";
    private String autoSyncUserFolderPath = "";
    private JTextField autoSyncUserFolderPathField;


    private ArrayList<JPanel> jPanels = new ArrayList<>();

    public DolphinINIGeneratorUI()
    {
        File chosenGameID = new File("chosenGameID.txt");
        if (chosenGameID.exists()) {
            chosenGameID.delete();
        }

        setTitle("Dolphin Emulator INI Generator");
        generateUI();

        File generatorSettings = new File("generatorSettings.txt");
        if (generatorSettings.exists()) {
            loadSettingsOnStartUp();
        }
    }

    private void generateUI() {
        JPanel mainMenuPanel = new JPanel();
        GridLayout mainMenuGridLayout = new GridLayout(3, 1);
        mainMenuPanel.setLayout(mainMenuGridLayout);

        JPanel generatorSettingsPanel = new JPanel();
        GridLayout generatorSettingsGridLayout = new GridLayout(2,4);
        generatorSettingsPanel.setLayout(generatorSettingsGridLayout);

        //main menu panel
        pickGame = new JButton("Pick Game for INI");
        pickGame.addActionListener(this);

        editINI = new JButton("Edit Generated INI");
        editINI.addActionListener(this);

        moveGameSettingsToUserFolder = new JButton("Move Game Settings to Dolphin User Folder");
        moveGameSettingsToUserFolder.addActionListener(this);

        //game settings panel
        autoSyncUserFolderPathLabel = new JLabel("Auto Sync User Folder Path");

        autoSyncUserFolderPathField = new JTextField(10);
        autoSyncUserFolderPathField.setEditable(false);

        browseForAutoSyncUserFolderPath = new JButton("Browse");
        browseForAutoSyncUserFolderPath.addActionListener(this);

        //purely for UI padding so the other options are in the correct spots
        JLabel paddingJLabel = new JLabel("");

        autoSyncEnabledLabel = new JLabel("Auto Sync Enabled");
        autoSyncEnabled = new JCheckBox();

        useCoverArtLabel = new JLabel("Use Cover Art for Editing INI UI");
        useCoverArt = new JCheckBox();

        autoSyncEnabled.addActionListener (e -> updateGeneratorSettings());
        useCoverArt.addActionListener (e -> updateGeneratorSettings());

        mainMenuPanel.add(pickGame);
        mainMenuPanel.add(editINI);
        mainMenuPanel.add(moveGameSettingsToUserFolder);

        generatorSettingsPanel.add(autoSyncUserFolderPathLabel);
        generatorSettingsPanel.add(autoSyncUserFolderPathField);
        generatorSettingsPanel.add(browseForAutoSyncUserFolderPath);
        generatorSettingsPanel.add(paddingJLabel);
        generatorSettingsPanel.add(autoSyncEnabledLabel);
        generatorSettingsPanel.add(autoSyncEnabled);
        generatorSettingsPanel.add(useCoverArtLabel);
        generatorSettingsPanel.add(useCoverArt);

        jPanels.add(mainMenuPanel);
        jPanels.add(generatorSettingsPanel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Main Menu", jPanels.get(0));
        tabbedPane.add("Generator Settings", jPanels.get(1));
        add(tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pickGame) {
            GamePickerUI gamePickerUI = new GamePickerUI();
            gamePickerUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            gamePickerUI.pack();
            gamePickerUI.setVisible(true);
        }

        if (e.getSource() == editINI) {
            GameSettingsEditorUI gameSettingsEditorUI = new GameSettingsEditorUI();
            gameSettingsEditorUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            gameSettingsEditorUI.pack();
            gameSettingsEditorUI.setVisible(true);
        }

        if (e.getSource() == moveGameSettingsToUserFolder) {
            JOptionPane.showMessageDialog(this, "Please select the location of your Dolphin User Folder!");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                userFolderPath = fileChooser.getSelectedFile().getAbsolutePath();
                GameSettingsMover gameSettingsMover = new GameSettingsMover();
                try {
                    int copyFilesDialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to move the Game Settings files to the folder " + userFolderPath + "?");
                    if (copyFilesDialogResult == JOptionPane.YES_OPTION) {
                        UserFolderValidator userFolderValidator = new UserFolderValidator();
                        if (userFolderValidator.isValidUserFolder(userFolderPath)) {
                            gameSettingsMover.moveGameSettings(userFolderPath);
                            JOptionPane.showMessageDialog(this, "Game Settings have been moved to User Folder!");
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "This isn't a valid User Folder!");
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                return;
            }
        }

        if (e.getSource() ==  browseForAutoSyncUserFolderPath) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                autoSyncUserFolderPath = fileChooser.getSelectedFile().getAbsolutePath();
                UserFolderValidator userFolderValidator = new UserFolderValidator();
                if (userFolderValidator.isValidUserFolder(autoSyncUserFolderPath)) {
                    autoSyncUserFolderPathField.setText(autoSyncUserFolderPath);
                    updateGeneratorSettings();
                }
                else {
                    JOptionPane.showMessageDialog(this, "This isn't a valid User Folder!");
                }
            } else {
                return;
            }
        }
    }

    private void updateGeneratorSettings() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter( new FileOutputStream("generatorSettings.txt"));
        }
        catch (FileNotFoundException f) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        outputStream.println(GeneratorSettings.AUTO_SYNC_PATH + ":" + autoSyncUserFolderPathField.getText().trim());
        outputStream.println(GeneratorSettings.AUTO_SYNC_ENABLED + ":" + autoSyncEnabled.isSelected());
        outputStream.println(GeneratorSettings.USE_COVER_ART + ":" + useCoverArt.isSelected());
        outputStream.close();
    }

    private void loadSettingsOnStartUp() {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("generatorSettings.txt"));
        } catch (FileNotFoundException e) {
            return;
        }

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();

            if (line.contains(GeneratorSettings.AUTO_SYNC_ENABLED)) {
                String settingValue = line.split(":")[1];
                autoSyncEnabled.setSelected(Boolean.parseBoolean(settingValue));
            }

            else if (line.contains(GeneratorSettings.AUTO_SYNC_PATH)) {
                String[] lineParts = line.split(":");
                if (lineParts.length > 1) {
                    autoSyncUserFolderPathField.setText(lineParts[1]);
                }
                else {
                    autoSyncUserFolderPathField.setText("");
                }
            }

            else if (line.contains(GeneratorSettings.USE_COVER_ART)) {
                String settingValue = line.split(":")[1];
                useCoverArt.setSelected(Boolean.parseBoolean(settingValue));
            }
        }

        inputStream.close();
    }
}