package constants;

public class ConfigOptions {

    public static final String[] TRUE_FALSE_OPTIONS = {"Use Global Setting", "True", "False"};
    public static final String[] GRAPHIC_BACKENDS = {"Use Global Setting", "Direct3D 11", "Direct3D 12", "OpenGL", "Vulkan", "Metal", "Software", "Null (No Output)"};
    public static final String[] CPU_EMULATOR_ENGINE = {"Use Global Setting", "Interpreter", "JIT x64", "JITIL", "JIT ARM64", "Cached Interpreter"};
    public static final String[] EMULATION_SPEEDS = {"Use Global Setting", "Unlimited", "10%", "20%", "30%", "40%", "50%",
            "60%","70%", "80%","90%", "100%","110%", "120%","130%", "140%","150%", "160%","170%", "180%", "190%", "200%"};
    public static final String[] OVER_CLOCK_SPEEDS = {"Use Global Setting", "10%", "20%", "30%", "40%", "50%",
            "60%","70%", "80%","90%", "100%","110%", "120%","130%", "140%","150%", "160%","170%", "180%", "190%", "200%"};

    public static final String[] GAMECUBE_LANGUAGE = {"Use Global Setting", "English/Japanese", "German", "French", "Spanish", "Italian", "Dutch"};

    public static final String[] GAMECUBE_SLOT = {"Use Global Setting", "Dummy", "Memory Card", "Microphone", "Gecko", "GCI Folder", "Advance Game Port", "Nothing"};
    public static final String[] GAMECUBE_SERIAL_PORT = {"Use Global Setting", "Broadband Adapter (TAP)", "Broadband Adapter (XLink Kai)", "Broadband Adapter (Built In)"};

    public static final String[] INTERNAL_RESOLUTIONS = {"Use Global Setting", "Native", "2x Native (720p)", "3x Native (1080p)", "4x Native (1440p)", "5x Native", "6x Native (4K)", "7x Native", "8x Native (5K)"};

    public static final String[] SHADER_COMPILATION_METHODS = {"Use Global Setting", "Specialized (Default)", "Exclusive Ubershaders", "Hybrid Ubershaders", "Skip Drawing"};
    public static final String[] TEXTURE_CACHE_ACCURACIES = {"Use Global Setting", "Safe", "Middle", "Fast"};
    public static final String[] ASPECT_RATIOS = {"Use Global Setting", "16:9", "4:3", "Stretch"};
    public static final String[] ANTI_ALIASING = {"Use Global Setting", "None", "2x","4x", "8x"};
    public static final String[] ANTI_ALIASING_METHODS = {"Use Global Setting", "MSAA", "SSAA"};
    public static final String[] ANISOTROPIC_FILTERING = {"Use Global Setting", "None", "2x","4x", "8x", "16x"};
    public static final String[] TEXTURE_FILTERING = {"Use Global Setting", "None", "Linear","Nearest"};
    public static final String[] WII_LANGUAGE = {"Use Global Setting", "Japanese", "English","German", "French"
            , "Spanish", "Italian", "Dutch", "Simplified Chinese", "Traditional Chinese", "Korean"};

    public static final String[] WII_ASPECT_RATIO = {"Use Global Setting", "4:3", "16:9"};
    public static final String[] GAMECUBE_CONTROLLER_TYPES = {"Use Global Setting", "Nothing", "GBA (TCP)", "Standard Controller"
            ,"Keyboard", "Steering Wheel", "Dance Mat", "DK Bongos", "GameCube Adapter for Wii U", "GBA (Integrated)"};

    public static final String[] WII_CONTROLLER_TYPES = {"Use Global Setting", "None", "Emulated Wiimote", "Real Wiimote"};
}
