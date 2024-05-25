package constants;

public class DifferingINIConfigOptions {
    
    public static final String[] DIFFERENT_CONFIG_OPTION_NAMES = {"Graphics Backend", "CPU Emulator Engine", "Emulation Speed", "CPU Overclock"
    , "GPU Overclock", "GameCube Language", "GameCube Slot A", "GameCube Slot B"
    , "GameCube Serial Port", "Internal Resolution", "Shader Compilation", "Texture Cache Accuracy"
    , "Aspect Ratio", "Anti-Aliasing", "Anti-Aliasing Method","Anisotropic Filtering"
    , "Force Texture Filtering", "Skip EFB Access From CPU", "Ignore Format Changes", "Manual Texture Sampling"
    , "Wii Language", "Wii Aspect Ratio"
    , "GameCube Controller Port 1",  "GameCube Controller Port 2", "GameCube Controller Port 3", "GameCube Controller Port 4"
    , "Wii Remote 1", "Wii Remote 2", "Wii Remote 3",  "Wii Remote 4"};

    public static final String[][] DIFFERING_CONFIG_OPTIONS = {ConfigOptions.GRAPHIC_BACKENDS, ConfigOptions.CPU_EMULATOR_ENGINE
            , ConfigOptions.EMULATION_SPEEDS, ConfigOptions.OVER_CLOCK_SPEEDS, ConfigOptions.OVER_CLOCK_SPEEDS, ConfigOptions.GAMECUBE_LANGUAGE
            , ConfigOptions.GAMECUBE_SLOT, ConfigOptions.GAMECUBE_SLOT, ConfigOptions.GAMECUBE_SERIAL_PORT, ConfigOptions.INTERNAL_RESOLUTIONS
            , ConfigOptions.SHADER_COMPILATION_METHODS, ConfigOptions.TEXTURE_CACHE_ACCURACIES, ConfigOptions.ASPECT_RATIOS, ConfigOptions.ANTI_ALIASING, ConfigOptions.ANTI_ALIASING_METHODS
            , ConfigOptions.ANISOTROPIC_FILTERING, ConfigOptions.TEXTURE_FILTERING, ConfigOptions.TRUE_FALSE_OPTIONS, ConfigOptions.TRUE_FALSE_OPTIONS, ConfigOptions.TRUE_FALSE_OPTIONS
            , ConfigOptions.WII_LANGUAGE, ConfigOptions.WII_ASPECT_RATIO, ConfigOptions.GAMECUBE_CONTROLLER_TYPES, ConfigOptions.GAMECUBE_CONTROLLER_TYPES
            , ConfigOptions.GAMECUBE_CONTROLLER_TYPES, ConfigOptions.GAMECUBE_CONTROLLER_TYPES, ConfigOptions.WII_CONTROLLER_TYPES
            , ConfigOptions.WII_CONTROLLER_TYPES, ConfigOptions.WII_CONTROLLER_TYPES, ConfigOptions.WII_CONTROLLER_TYPES};

    public static final String[][] DIFFERING_INI_CONFIG_OPTIONS = {INIConfigOptions.GRAPHIC_BACKENDS, INIConfigOptions.CPU_EMULATOR_ENGINE
            , INIConfigOptions.EMULATION_SPEEDS, INIConfigOptions.OVER_CLOCK_SPEEDS, INIConfigOptions.OVER_CLOCK_SPEEDS, INIConfigOptions.GAMECUBE_LANGUAGE
            , INIConfigOptions.GAMECUBE_SLOT, INIConfigOptions.GAMECUBE_SLOT, INIConfigOptions.GAMECUBE_SERIAL_PORT, INIConfigOptions.INTERNAL_RESOLUTIONS
            , INIConfigOptions.SHADER_COMPILATION_METHODS, INIConfigOptions.TEXTURE_CACHE_ACCURACIES, INIConfigOptions.ASPECT_RATIOS, INIConfigOptions.ANTI_ALIASING, INIConfigOptions.ANTI_ALIASING_METHODS
            , INIConfigOptions.ANISOTROPIC_FILTERING, INIConfigOptions.TEXTURE_FILTERING, INIConfigOptions.INVERSE_TRUE_FALSE_OPTIONS, INIConfigOptions.INVERSE_TRUE_FALSE_OPTIONS, INIConfigOptions.INVERSE_TRUE_FALSE_OPTIONS
            , INIConfigOptions.WII_LANGUAGE, INIConfigOptions.WII_ASPECT_RATIO, INIConfigOptions.GAMECUBE_CONTROLLER_TYPES, INIConfigOptions.GAMECUBE_CONTROLLER_TYPES
            , INIConfigOptions.GAMECUBE_CONTROLLER_TYPES, INIConfigOptions.GAMECUBE_CONTROLLER_TYPES, INIConfigOptions.WII_CONTROLLER_TYPES
            , INIConfigOptions.WII_CONTROLLER_TYPES, INIConfigOptions.WII_CONTROLLER_TYPES, INIConfigOptions.WII_CONTROLLER_TYPES};
}
