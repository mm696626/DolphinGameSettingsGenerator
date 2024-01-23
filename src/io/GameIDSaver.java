package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GameIDSaver {

    public void saveGameIDChoiceToGameIDChoiceFile(String gameID)
    {
        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter( new FileOutputStream("chosenGameID.txt"));
        }
        catch (FileNotFoundException f) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        outputStream.println(gameID);
        outputStream.close();
    }
}