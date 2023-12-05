package icu.lama.narchook;

public class FakeDeviceID {
    public static native void prepareRandom(int seed);
    public static native String randomDeviceID();
    public static native void setFakeDeviceID(String newDeviceID);
    public static native void toggleFeature(boolean status);
}
