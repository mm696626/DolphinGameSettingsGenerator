package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameIDLoader {

    public String getGameIDFromChosenGameIDFile() {
        String gameID = "";
        Scanner inputStream = null;

        try {
            inputStream = new Scanner (new FileInputStream("chosenGameID.txt"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Chosen Game ID file does not exist");
            return null;
        }

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            if (line.length() > 0) {
                gameID = line;
                inputStream.close();
                return gameID;
            }
        }

        inputStream.close();
        return null;
    }
}
