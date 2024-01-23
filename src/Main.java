// Dolphin Game Settings Generator by Matt McCullough
// This is to automate per game settings on the Dolphin Emulator

import ui.DolphinINIGeneratorUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DolphinINIGeneratorUI dolphinINIGeneratorUI = new DolphinINIGeneratorUI();
        dolphinINIGeneratorUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dolphinINIGeneratorUI.pack();
        dolphinINIGeneratorUI.setVisible(true);
    }
}