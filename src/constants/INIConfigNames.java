package constants;

public class INIConfigNames {

    public static final String[] INICoreOptions = {"CPUThread", "GFXBackend", "CPUCore", "DSPHLE", "EmulationSpeed"
            ,"Overclock", "SyncGPU", "SyncGpuOverclock", "FastDiscSpeed", "ProgressiveScan", "GameCubeLanguage"
            , "SlotA", "SlotB", "SerialPort1"};

    public static final String[] INIVideoSettingsOptions = {"InternalResolution", "ShaderCompilationMode", "WaitForShadersBeforeStarting"
            ,"SafeTextureCacheColorSamples", "EnableGPUTextureDecoding", "AspectRatio", "wideScreenHack", "MSAA", "SSAA"
            ,"FastDepthCalc", "EnablePixelLighting", "DisableFog", "HiresTextures", "CacheHiresTextures", "Crop", "CPUCull"};

    public static final String[] INIVideoEnhancementsOptions = {"MaxAnisotropy", "ForceTextureFiltering", "ForceTrueColor"
            ,"DisableCopyFilter", "ArbitraryMipmapDetection"};

    public static final String[] INIVideoHacksOptions = {"EFBToTextureEnable", "EFBScaledCopy", "EFBAccessEnable"
            ,"EFBEmulateFormatChanges", "BBoxEnable", "VertexRounding", "XFBToTextureEnable", "ImmediateXFBEnable"
            ,"SkipDuplicateXFBs", "DeferEFBCopies", "EFBAccessDeferInvalidation", "FastTextureSampling"};

    public static final String[] INIVideoHardwareOption = {"VSync"};
    public static final String[] INIDspAudioOption = {"Volume"};
    public static final String[] INIWiiOptions = {"Language", "Widescreen"};
    public static final String[] INIControlOptions = {"PadType0", "PadType1", "PadType2", "PadType3"
            , "PadProfile1", "PadProfile2", "PadProfile3", "PadProfile4"
            ,"WiimoteSource0", "WiimoteSource1", "WiimoteSource2", "WiimoteSource3"
            ,"WiimoteProfile1", "WiimoteProfile2", "WiimoteProfile3", "WiimoteProfile4"};
}
