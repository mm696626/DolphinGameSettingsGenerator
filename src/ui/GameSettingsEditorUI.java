package ui;

import validation.ExtensionValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GameSettingsEditorUI extends JFrame implements ActionListener {

    private ArrayList<File> gameSettingsFiles;
    private JButton[] gameSettingsButtons;
    private JButton pickOtherGameSettingsFile;
    private String iniFilePath;
    private String[] gameIDs;
    private Container container;

    public GameSettingsEditorUI() {
        setTitle("Game Setting Editor");

        //use WiiTDB file to get folder path
        File tempSettingsFile = new File("wiitdb.txt");
        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);
        String wiiTDBFilePathFolder = wiiTDBFilePath.substring(0, wiiTDBFilePath.lastIndexOf(filePathSeparator));

        File gameSettingsFolder = new File(wiiTDBFilePathFolder + filePathSeparator + "GameSettings");


        if (gameSettingsFolder.exists()) {
            gameSettingsFiles = getGameSettingFileList(gameSettingsFolder.getAbsolutePath());
        }
        else {
            gameSettingsFiles = new ArrayList<>();
        }

        this.container = getContentPane();
        container.setLayout(new BorderLayout());
        generateUI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkIfIDButtonWasPressed(e)) {
            setVisible(false);
        }

        if (e.getSource() == pickOtherGameSettingsFile) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                iniFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                ExtensionValidator extensionValidator = new ExtensionValidator();
                if (extensionValidator.isExtensionValid(iniFilePath)) {
                    GameSettingsGeneratorUI gameSettingsGeneratorUI = new GameSettingsGeneratorUI(null, true, iniFilePath);
                    gameSettingsGeneratorUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameSettingsGeneratorUI.pack();
                    gameSettingsGeneratorUI.setVisible(true);
                    setVisible(false);
                }
            } else {
                return;
            }
        }
    }

    private boolean checkIfIDButtonWasPressed(ActionEvent e) {
        for (int i=0; i<gameSettingsButtons.length; i++) {
            if (e.getSource() == gameSettingsButtons[i]) {
                String gameID = gameIDs[i];
                GameSettingsGeneratorUI gameSettingsGeneratorUI = new GameSettingsGeneratorUI(gameID, true, null);
                gameSettingsGeneratorUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameSettingsGeneratorUI.pack();
                gameSettingsGeneratorUI.setVisible(true);
                return true;
            }
        }

        return false;
    }

    private ArrayList<File> getGameSettingFileList(String gameSettingFolderPath) {

        File[] gameSettingFolderFileList = new File(gameSettingFolderPath).listFiles();
        ExtensionValidator extensionValidator = new ExtensionValidator();

        ArrayList<File> gameSettingFileList = new ArrayList<>();

        //Grab all gameSetting ini files and check subfolders if the file is a directory
        for (File file: gameSettingFolderFileList) {
            String fileName = file.getName();
            if (extensionValidator.isExtensionValid(fileName) && !file.isDirectory()) {
                gameSettingFileList.add(file);
            }
            else if (file.isDirectory()) {
                gameSettingFileList.addAll(getGameSettingFileList(file.getAbsolutePath()));
            }
        }

        return gameSettingFileList;
    }

    private void generateUI() {
        JPanel jPanel = new JPanel();

        int buttonsPerRow = 3;

        GridLayout gridLayout = new GridLayout((gameSettingsFiles.size()/buttonsPerRow)+1, buttonsPerRow);
        jPanel.setLayout(gridLayout);

        gameSettingsButtons = new JButton[gameSettingsFiles.size()];
        gameIDs = new String[gameSettingsFiles.size()];

        for (int i=0; i<gameSettingsFiles.size(); i++) {
            JButton jButton = new JButton();
            jButton.addActionListener(this);
            String gameID = gameSettingsFiles.get(i).getName();
            gameIDs[i] = gameID.substring(0, gameID.lastIndexOf("."));

            ImageIcon coverArt = getCoverArtForID(gameID);

            if (coverArt != null) {
                jButton.setIcon(coverArt);
            }
            else {
                jButton.setText(gameID);
            }

            gameSettingsButtons[i] = jButton;
            jPanel.add(gameSettingsButtons[i]);
        }

        pickOtherGameSettingsFile = new JButton("Choose Other Game Settings File");
        pickOtherGameSettingsFile.addActionListener(this);
        jPanel.add(pickOtherGameSettingsFile);

        //add scroll bar just in case
        JScrollPane jScrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(jScrollPane);
        add(jPanel);
    }

    private ImageIcon getCoverArtForID(String gameID) {
        //use WiiTDB file to get folder path
        File tempSettingsFile = new File("wiitdb.txt");
        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);
        String wiiTDBFilePathFolder = wiiTDBFilePath.substring(0, wiiTDBFilePath.lastIndexOf(filePathSeparator));
        File coversFolder = new File(wiiTDBFilePathFolder + filePathSeparator + "covers");

        gameID = gameID.substring(0, gameID.lastIndexOf("."));

        File image = new File(coversFolder + filePathSeparator + gameID + ".png");
        if (image.exists()) {
            return new ImageIcon(coversFolder + filePathSeparator + gameID + ".png");
        }
        else {
            return null;
        }
    }


}
