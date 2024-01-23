package constants;

public class ConfigOptions {

    public static final String[] trueFalseOptions = {"Use Global Setting", "True", "False"};
    public static final String[] graphicBackends = {"Use Global Setting", "Direct3D 11", "Direct3D 12", "OpenGL", "Vulkan", "Metal", "Software", "Null (No Output)"};
    public static final String[] cpuEmulatorEngine = {"Use Global Setting", "JIT x64", "JITIL", "JIT ARM64", "Cached Interpreter"};
    public static final String[] emulationSpeeds = {"Use Global Setting", "Unlimited", "10%", "20%", "30%", "40%", "50%",
            "60%","70%", "80%","90%", "100%","110%", "120%","130%", "140%","150%", "160%","170%", "180%", "190%", "200%"};
    public static final String[] overClockSpeeds = {"Use Global Setting", "10%", "20%", "30%", "40%", "50%",
            "60%","70%", "80%","90%", "100%","110%", "120%","130%", "140%","150%", "160%","170%", "180%", "190%", "200%"};

    public static final String[] gameCubeLanguage = {"Use Global Setting", "English/Japanese", "German", "French", "Spanish", "Italian", "Dutch"};

    public static final String[] gameCubeSlot = {"Use Global Setting", "Dummy", "Memory Card", "Microphone", "Gecko", "GCI Folder", "Advance Game Port", "Nothing"};
    public static final String[] gameCubeSerialPort = {"Use Global Setting", "Broadband Adapter (TAP)", "Broadband Adapter (XLink Kai)", "Broadband Adapter (Built In)"};

    public static final String[] internalResolutions = {"Use Global Setting", "Native", "2x Native (720p)", "3x Native (1080p)", "4x Native (1440p)", "5x Native", "6x Native (4K)", "7x Native", "8x Native (5K)"};

    public static final String[] shaderCompilationMethods = {"Use Global Setting", "Specialized (Default)", "Exclusive Ubershaders", "Hybrid Ubershaders", "Skip Drawing"};
    public static final String[] textureCacheAccuracies = {"Use Global Setting", "Safe", "Middle", "Fast"};
    public static final String[] aspectRatios = {"Use Global Setting", "4:3", "16:9", "Stretch"};
    public static final String[] antiAliasing = {"Use Global Setting", "None", "2x","4x", "8x"};
    public static final String[] antiAliasingMethods = {"Use Global Setting", "MSAA", "SSAA"};
    public static final String[] anisotropicFiltering = {"Use Global Setting", "None", "2x","4x", "8x", "16x"};
    public static final String[] textureFiltering = {"Use Global Setting", "None", "Linear","Nearest"};
    public static final String[] outputResampling = {"Use Global Setting", "Default", "Bilinear","BSpline", "Mitchell Netravali", "Catmull Rom", "Sharp Bilinear", "Area Sampling"};
    public static final String[] wiiLanguage = {"Use Global Setting", "Japanese", "English","German", "French"
            , "Spanish", "Italian", "Dutch", "Simplified Chinese", "Traditional Chinese", "Korean"};

    public static final String[] wiiAspectRatio = {"Use Global Setting", "4:3", "16:9"};
    public static final String[] gameCubeControllerTypes = {"Use Global Setting", "Nothing", "GBA (TCP)", "Standard Controller"
            ,"Keyboard", "Steering Wheel", "Dance Mat", "DK Bongos", "GameCube Adapter for Wii U", "GBA (Integrated)"};

    public static final String[] wiiControllerTypes = {"Use Global Setting", "None", "Emulated Wiimote", "Real Wiimote"};
}
