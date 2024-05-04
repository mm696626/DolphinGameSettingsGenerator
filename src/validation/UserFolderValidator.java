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

        return validFolders >= UserFolderFolders.latestBetaFolders.length
                || validFolders >= UserFolderFolders.latestStableFolders.length
                || validFolders >= UserFolderFolders.bareMinimumFolders.length;
    }

    private boolean isFolderNameValid(String folderName) {
        for (int i=0; i< UserFolderFolders.latestBetaFolders.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.latestBetaFolders[i])) {
                return true;
            }
        }

        for (int i=0; i< UserFolderFolders.latestStableFolders.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.latestStableFolders[i])) {
                return true;
            }
        }

        for (int i=0; i< UserFolderFolders.bareMinimumFolders.length; i++) {
            if (folderName.equalsIgnoreCase(UserFolderFolders.bareMinimumFolders[i])) {
                return true;
            }
        }

        return false;
    }
}
