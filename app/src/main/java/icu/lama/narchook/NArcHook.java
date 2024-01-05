package icu.lama.narchook;

import android.os.Build;
import android.util.Log;

import java.util.Arrays;

public class NArcHook {
    public static int NARCHOOK_API_VERSION = 10;

    public static native void toggleFeature(int feature, boolean status);
    public static native int getVersion();
    public static native void end();

    public static boolean init() {
        if (!Arrays.asList(Build.SUPPORTED_64_BIT_ABIS).contains("arm64-v8a")) {
            Log.e("narchook-japi", "Unsupported ABI: " + Arrays.toString(Build.SUPPORTED_64_BIT_ABIS));
            return false;
        }

        try {
            System.loadLibrary("narchook");
        } catch (Throwable e) {
            Log.e("narchook-japi", "Failed to load native library", e);
            return false;
        }

        if (NArcHook.getVersion() != NArcHook.NARCHOOK_API_VERSION) {
            Log.e("narchook-japi", "API version mismatch! Expected: " + NArcHook.NARCHOOK_API_VERSION + " Got: " + NArcHook.getVersion());
            return false;
        }

        return true;
    }
}
