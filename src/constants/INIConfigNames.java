package constants;

public class INIConfigNames {

    public static final String[] INI_CORE_OPTIONS = {"CPUThread", "GFXBackend", "CPUCore", "DSPHLE", "EmulationSpeed"
            ,"Overclock", "SyncGPU", "SyncGpuOverclock", "FastDiscSpeed", "ProgressiveScan", "GameCubeLanguage"
            , "SlotA", "SlotB", "SerialPort1"};

    public static final String[] INI_VIDEO_SETTINGS_OPTIONS = {"InternalResolution", "ShaderCompilationMode", "WaitForShadersBeforeStarting"
            ,"SafeTextureCacheColorSamples", "EnableGPUTextureDecoding", "AspectRatio", "wideScreenHack", "MSAA", "SSAA"
            ,"FastDepthCalc", "EnablePixelLighting", "DisableFog", "HiresTextures", "CacheHiresTextures", "Crop", "CPUCull"};

    public static final String[] INI_VIDEO_ENHANCEMENTS_OPTIONS = {"MaxAnisotropy", "ForceTextureFiltering", "ForceTrueColor"
            ,"DisableCopyFilter", "ArbitraryMipmapDetection"};

    public static final String[] INI_VIDEO_HACKS_OPTIONS = {"EFBToTextureEnable", "EFBScaledCopy", "EFBAccessEnable"
            ,"EFBEmulateFormatChanges", "BBoxEnable", "VertexRounding", "XFBToTextureEnable", "ImmediateXFBEnable"
            ,"SkipDuplicateXFBs", "DeferEFBCopies", "EFBAccessDeferInvalidation", "FastTextureSampling"};

    public static final String[] INI_VIDEO_HARDWARE_OPTION = {"VSync"};
    public static final String[] INI_DSP_AUDIO_OPTION = {"Volume"};
    public static final String[] INI_WII_OPTIONS = {"Language", "Widescreen"};
    public static final String[] INI_CONTROL_OPTIONS = {"PadType0", "PadType1", "PadType2", "PadType3"
            , "PadProfile1", "PadProfile2", "PadProfile3", "PadProfile4"
            ,"WiimoteSource0", "WiimoteSource1", "WiimoteSource2", "WiimoteSource3"
            ,"WiimoteProfile1", "WiimoteProfile2", "WiimoteProfile3", "WiimoteProfile4"};
}
