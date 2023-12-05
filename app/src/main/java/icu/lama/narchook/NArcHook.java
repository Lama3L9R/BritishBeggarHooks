package icu.lama.narchook;

public class NArcHook {
    public static int NARCHOOK_API_VERSION = 9;

    public static native void toggleFeature(int feature, boolean status);
    public static native int getVersion();
    public static native void end();

    public static void init() {
        System.loadLibrary("narchook");
    }
}
