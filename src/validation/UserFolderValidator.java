package validation;

import constants.UserFolderFolders;

import java.io.File;

public class UserFolderValidator {

    public boolean isValidUserFolder(String userFolderPath) {
        File userFolder = new File(userFolderPath);
        File[] userFolderFiles = userFolder.listFiles();
        int validFolders = 0;
        for (int i=0; i<userFolderFiles.length; i++) {
            if (userFolderFiles[i].isDirectory() && isFolderNameValid(userFolderFiles[i].getName())) {
                validFolders++;
            }
        }

        return validFolders >= UserFolderFolders.LATEST_BETA_FOLDERS.length
                || validFolders >= UserFolderFolders.LATEST_STABLE_FOLDERS.length
                || validFolders >= UserFolderFolders.BARE_MINIMUM_FOLDERS.length;
    }

    private boolean isFolderNameValid(String folderName) {
        for (int i = 0; i< UserFolderFolders.LATEST_BETA_FOLDERS.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.LATEST_BETA_FOLDERS[i])) {
                return true;
            }
        }

        for (int i = 0; i< UserFolderFolders.LATEST_STABLE_FOLDERS.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.LATEST_STABLE_FOLDERS[i])) {
                return true;
            }
        }

        for (int i = 0; i< UserFolderFolders.BARE_MINIMUM_FOLDERS.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.BARE_MINIMUM_FOLDERS[i])) {
                return true;
            }
        }

        return false;
    }
}
