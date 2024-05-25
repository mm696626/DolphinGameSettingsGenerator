package constants;

public class ConfigNames {

    public static final String[] CORE_OPTIONS = {"Dual Core", "Graphics Backend", "CPU Emulator Engine", "DSP HLE", "Emulation Speed"
            ,"CPU Overclock", "Synchronize GPU Thread", "GPU Overclock", "Speed up Disc Transfer Rate", "Progressive Scan", "GameCube Language"
            , "GameCube Slot A", "GameCube Slot B", "GameCube Serial Port"};

    public static final String[] VIDEO_SETTINGS_OPTIONS = {"Internal Resolution", "Shader Compilation", "Compile Shaders Before Starting"
            ,"Texture Cache Accuracy", "GPU Texture Decoding", "Aspect Ratio", "Widescreen Hack", "Anti-Aliasing", "Anti-Aliasing Method"
            ,"Fast Depth Calculation", "Per-Pixel Lighting", "Disable Fog", "Load Custom Textures", "Prefetch Custom Textures", "Crop to 4:3 or 16:9", "Cull Vertices on the CPU"};

    public static final String[] VIDEO_ENHANCEMENTS_OPTIONS = {"Anisotropic Filtering", "Force Texture Filtering", "Force 24-Bit Color"
            ,"Disable Copy Filter", "Arbitrary Mipmap Detection"};

    public static final String[] VIDEO_HACKS_OPTIONS = {"EFB Copies To Texture", "Scaled EFB Copy", "Skip EFB Access From CPU"
            ,"Ignore Format Changes", "Bounding Box", "Vertex Rounding", "XFB Copies to Texture", "Immediate XFB"
            ,"Skip Presenting Duplicate Frames", "Defer EFB copies to RAM", "Defer EFB Cache Invalidation", "Manual Texture Sampling"};

    public static final String[] VIDEO_HARDWARE_OPTION = {"V-Sync"};
    public static final String[] DSP_AUDIO_OPTION = {"Volume"};
    public static final String[] WII_OPTIONS = {"Wii Language", "Wii Aspect Ratio"};
    public static final String[] CONTROL_OPTIONS = {"GameCube Controller Port 1", "GameCube Controller Port 2", "GameCube Controller Port 3", "GameCube Controller Port 4"
            , "GameCube Controller Port 1 Profile", "GameCube Controller Port 2 Profile", "GameCube Controller Port 3 Profile", "GameCube Controller Port 4 Profile"
            ,"Wii Remote 1", "Wii Remote 2", "Wii Remote 3", "Wii Remote 4"
            ,"Wii Remote 1 Profile", "Wii Remote 2 Profile", "Wii Remote 3 Profile", "Wii Remote 4 Profile"};
}
