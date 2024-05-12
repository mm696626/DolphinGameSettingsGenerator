package ui;

import io.GameSettingsMover;
import validation.UserFolderValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DolphinINIGeneratorUI extends JFrame implements ActionListener {


    private JButton pickGame, editINI, moveGameSettingsToUserFolder;
    private String userFolderPath = "";
    GridBagConstraints gridBagConstraints = null;

    public DolphinINIGeneratorUI()
    {

        File chosenGameID = new File("chosenGameID.txt");
        if (chosenGameID.exists()) {
            chosenGameID.delete();
        }

        setTitle("Dolphin Emulator INI Generator");

        pickGame = new JButton("Pick Game for INI");
        pickGame.addActionListener(this);

        editINI = new JButton("Edit Generated INI");
        editINI.addActionListener(this);

        moveGameSettingsToUserFolder = new JButton("Move Game Settings to Dolphin User Folder");
        moveGameSettingsToUserFolder.addActionListener(this);

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        add(pickGame, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(editINI, gridBagConstraints);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=0;
        add(moveGameSettingsToUserFolder, gridBagConstraints);
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
    }
}