package ui;

import io.GameIDLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DolphinINIGeneratorUI extends JFrame implements ActionListener {


    private JButton pickGame, generateINI;
    private String gameID = "";
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

        generateINI = new JButton("Pick Game Settings for INI");
        generateINI.addActionListener(this);

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        add(pickGame, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(generateINI, gridBagConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == pickGame) {
            GamePickerUI gamePickerUI = new GamePickerUI();
            gamePickerUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            gamePickerUI.pack();
            gamePickerUI.setVisible(true);
        }

        if (e.getSource() == generateINI) {

            File chosenGameID = new File("chosenGameID.txt");
            if (chosenGameID.exists()) {
                setVisible(false);

                GameIDLoader gameIDLoader = new GameIDLoader();
                gameID = gameIDLoader.getGameIDFromChosenGameIDFile();

                GameSettingsGeneratorUI gameSettingsGeneratorUI = new GameSettingsGeneratorUI(gameID);
                gameSettingsGeneratorUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameSettingsGeneratorUI.pack();
                gameSettingsGeneratorUI.setVisible(true);
            }

            else {
                JOptionPane.showMessageDialog(this, "No game has been chosen!");
            }
        }
    }
}