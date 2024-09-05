package io;

import constants.GeneratorSettings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneratorSettingsLoader {

    public String getAutoMovePath() {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("generatorSettings.txt"));
        } catch (FileNotFoundException e) {
            return "";
        }

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();

            if (line.contains(GeneratorSettings.AUTO_MOVE_PATH)) {
                String[] lineParts = line.split("=");
                inputStream.close();
                if (lineParts.length > 1) {
                    return lineParts[1];
                }
                else {
                    return "";
                }
            }
        }

        inputStream.close();
        return "";
    }

    public boolean getUseCoverArt() {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("generatorSettings.txt"));
        } catch (FileNotFoundException e) {
            return false;
        }

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();

            if (line.contains(GeneratorSettings.USE_COVER_ART)) {
                String settingValue = line.split("=")[1];
                inputStream.close();
                return Boolean.parseBoolean(settingValue);
            }
        }

        inputStream.close();
        return false;
    }

}
