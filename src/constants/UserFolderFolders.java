package constants;

public class UserFolderFolders {

    //these are the directories created in the user folder when you freshly install Dolphin
    //the latest beta is the latest beta version as of 5/4/2024 (5.0-21460)
    //the latest stable is the latest stable version as of 5/4/2024 (5.0)
    //the bare minimum is in case Dolphin updates and folders change names

    public static final String[] LATEST_BETA_FOLDERS = {"Cache", "Config", "Dump", "GameSettings", "GBA", "GC", "Load", "Logs",
            "Maps", "ResourcePacks", "SavedAssembly", "ScreenShots", "Shaders", "StateSaves", "Styles", "Themes", "Wii"};
    public static final String[] LATEST_STABLE_FOLDERS = {"Cache", "Config", "Dump", "GameSettings", "GC", "Load", "Maps",
            "ScreenShots", "Shaders", "StateSaves", "Themes", "Wii"};

    public static final String[] BARE_MINIMUM_FOLDERS = {"Config", "GameSettings", "GC", "Wii"};

}
