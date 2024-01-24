package constants;

public class DifferingINIConfigOptions {
    
    public static final String[] differentConfigOptionNames = {"Graphics Backend", "CPU Emulator Engine", "Emulation Speed", "CPU Overclock"
    , "GPU Overclock", "GameCube Language", "GameCube Slot A", "GameCube Slot B"
    , "GameCube Serial Port", "Internal Resolution", "Shader Compilation", "Texture Cache Accuracy"
    , "Aspect Ratio", "Anti-Aliasing", "Anti-Aliasing Method","Anisotropic Filtering"
    , "Force Texture Filtering", "Skip EFB Access From CPU", "Ignore Format Changes", "Manual Texture Sampling"
    , "Wii Language", "Wii Aspect Ratio"
    , "GameCube Controller Port 1",  "GameCube Controller Port 2", "GameCube Controller Port 3", "GameCube Controller Port 4"
    , "Wii Remote 1", "Wii Remote 2", "Wii Remote 3",  "Wii Remote 4"};

    public static final String[][] differingConfigOptions = {ConfigOptions.graphicBackends, ConfigOptions.cpuEmulatorEngine
            , ConfigOptions.emulationSpeeds, ConfigOptions.overClockSpeeds, ConfigOptions.overClockSpeeds, ConfigOptions.gameCubeLanguage
            , ConfigOptions.gameCubeSlot, ConfigOptions.gameCubeSlot, ConfigOptions.gameCubeSerialPort, ConfigOptions.internalResolutions
            , ConfigOptions.shaderCompilationMethods, ConfigOptions.textureCacheAccuracies, ConfigOptions.aspectRatios, ConfigOptions.antiAliasing, ConfigOptions.antiAliasingMethods
            , ConfigOptions.anisotropicFiltering, ConfigOptions.textureFiltering, ConfigOptions.trueFalseOptions, ConfigOptions.trueFalseOptions, ConfigOptions.trueFalseOptions
            , ConfigOptions.wiiLanguage, ConfigOptions.wiiAspectRatio, ConfigOptions.gameCubeControllerTypes, ConfigOptions.gameCubeControllerTypes
            , ConfigOptions.gameCubeControllerTypes, ConfigOptions.gameCubeControllerTypes, ConfigOptions.wiiControllerTypes
            , ConfigOptions.wiiControllerTypes, ConfigOptions.wiiControllerTypes, ConfigOptions.wiiControllerTypes};

    public static final String[][] differingINIConfigOptions = {INIConfigOptions.graphicBackends, INIConfigOptions.cpuEmulatorEngine
            , INIConfigOptions.emulationSpeeds, INIConfigOptions.overClockSpeeds, INIConfigOptions.overClockSpeeds, INIConfigOptions.gameCubeLanguage
            , INIConfigOptions.gameCubeSlot, INIConfigOptions.gameCubeSlot, INIConfigOptions.gameCubeSerialPort, INIConfigOptions.internalResolutions
            , INIConfigOptions.shaderCompilationMethods, INIConfigOptions.textureCacheAccuracies, INIConfigOptions.aspectRatios, INIConfigOptions.antiAliasing, INIConfigOptions.antiAliasingMethods
            , INIConfigOptions.anisotropicFiltering, INIConfigOptions.textureFiltering, INIConfigOptions.inverseTrueFalseOptions, INIConfigOptions.inverseTrueFalseOptions, INIConfigOptions.inverseTrueFalseOptions
            , INIConfigOptions.wiiLanguage, INIConfigOptions.wiiAspectRatio, INIConfigOptions.gameCubeControllerTypes, INIConfigOptions.gameCubeControllerTypes
            , INIConfigOptions.gameCubeControllerTypes, INIConfigOptions.gameCubeControllerTypes, INIConfigOptions.wiiControllerTypes
            , INIConfigOptions.wiiControllerTypes, INIConfigOptions.wiiControllerTypes, INIConfigOptions.wiiControllerTypes};
}
