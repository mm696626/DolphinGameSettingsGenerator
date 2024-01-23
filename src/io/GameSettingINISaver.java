package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;

public class GameSettingINISaver {

    private String gameID = "";

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

        outputStream.close();
    }
}
