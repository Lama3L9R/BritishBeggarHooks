package icu.lama.ukbeggar.hooks;

public class NArcHook {
    public static final int ARCAEA_VERSION_UNKNOWN = 0;
    public static final int ARCAEA_VERSION_PLAYSTORE = 1;
    public static final int ARCAEA_VERSION_CHINA = 2;

    public native static void setUrl(String url);
    public native static void enable(boolean status);
    public native static String getTargetVersion();
    public native static boolean isVersionSupported(int version);
    public native static void setHookTarget(int version);
    public native static void enableFakeDeviceID(boolean status);
    public native static void setFakeDeviceID(String id);
}
