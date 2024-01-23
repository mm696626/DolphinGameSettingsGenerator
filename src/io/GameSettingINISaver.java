package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
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

        }

        else if (settingBlockHeader.equals("[Video_Settings]")) {

        }

        else if (settingBlockHeader.equals("[Video_Enhancements]")) {

        }

        else if (settingBlockHeader.equals("[Video_Hardware]")) {

        }

        else if (settingBlockHeader.equals("[DSP]")) {

        }

        else if (settingBlockHeader.equals("[Wii]")) {

        }

        else if (settingBlockHeader.equals("[Controls]")) {

        }

        else {
            return "";
        }

        return "";
    }
}
