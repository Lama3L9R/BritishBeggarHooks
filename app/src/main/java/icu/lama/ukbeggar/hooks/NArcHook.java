package icu.lama.ukbeggar.hooks;

public class NArcHook {
    public static final int NARCHOOK_API_VERSION = 6;

    public static final int ARCAEA_VERSION_UNKNOWN = 0;
    public static final int ARCAEA_VERSION_PLAYSTORE = 1;
    public static final int ARCAEA_VERSION_CHINA = 2;
    public static final int FEAT_ARC_PLAYSTORE = 0;
    public static final int FEAT_ARC_CHINA = 1;
    public static final int FEAT_HOOK_SEARCHING = 2;
    public static final int HOOKING_USE_OFFSET = 0;
    public static final int HOOKING_USE_GENERIC_SEARCH = 1;
    public static final int HOOKING_USE_CUSTOM_OFFSET = 2;
    public static final int HOOKING_USE_CUSTOM_SEARCH = 3;
    public static final int HOOKING_FN_VSETOPT = 0;

    public static void init() {
        System.loadLibrary("narchook");
    }

    public native static void setUrl(String url);
    public native static void enable(boolean status);
    public native static String getTargetVersion();
    @Deprecated public native static boolean isVersionSupported(int version);
    public native static void setHookTarget(int version);
    public native static void enableFakeDeviceID(boolean status);
    public native static void setFakeDeviceID(String id);
    public native static boolean isFeatureSupported(long feature);
    public native static void overridePattern(byte[] pattern);
    public native static void overrideOffset(int fn, int offset);
    public native static void overrideHookingMethod(int method);
    public native static int getVersion();
}
