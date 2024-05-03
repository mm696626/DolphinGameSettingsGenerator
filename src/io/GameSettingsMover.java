package io;

import validation.ExtensionValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GameSettingsMover {

    public void moveGameSettings(String userFolderPath) throws IOException {
        //use WiiTDB file to get folder path
        File tempSettingsFile = new File("wiitdb.txt");
        String wiiTDBFilePath = tempSettingsFile.getAbsolutePath();
        int fileNameIndex = wiiTDBFilePath.lastIndexOf("wiitdb.txt");
        String filePathSeparator = wiiTDBFilePath.substring(fileNameIndex-1, fileNameIndex);
        String wiiTDBFilePathFolder = wiiTDBFilePath.substring(0, wiiTDBFilePath.lastIndexOf(filePathSeparator));

        File gameSettingsFolder = new File(wiiTDBFilePathFolder + filePathSeparator + "GameSettings");
        if (!gameSettingsFolder.exists()) {
            gameSettingsFolder.mkdirs();
        }

        //should never execute the make directory here since your user folder should always have this folder
        File userFolderGameSettingsFolder = new File(userFolderPath + filePathSeparator + "GameSettings");
        if (!userFolderGameSettingsFolder.exists()) {
            userFolderGameSettingsFolder.mkdirs();
        }

        File[] settingsFiles = gameSettingsFolder.listFiles();
        ExtensionValidator extensionValidator = new ExtensionValidator();
        for (int i=0; i<settingsFiles.length; i++)  {
            String settingFilePath = settingsFiles[i].getAbsolutePath();

            if (extensionValidator.isExtensionValid(settingsFiles[i].getName())) {
                String fileExtension = settingFilePath.substring(settingFilePath.lastIndexOf("."));
                String gameID = settingFilePath.substring(settingFilePath.lastIndexOf(filePathSeparator)+1, settingFilePath.lastIndexOf("."));
                String copiedSettingFilePath = userFolderPath + filePathSeparator + "GameSettings" + filePathSeparator + gameID + fileExtension;
                File copiedSettingsFile = new File(copiedSettingFilePath);
                Files.move(settingsFiles[0].toPath(), copiedSettingsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
