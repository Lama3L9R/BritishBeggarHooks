package icu.lama.ukbeggar.hooks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XpMain implements IXposedHookLoadPackage {
    private static final String TARGET_PACKAGE = "moe.low.arc";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (TARGET_PACKAGE.equals(loadPackageParam.packageName)) {
            XSharedPreferences prefs = new XSharedPreferences("icu.lama.ukbeggar.hooks", "preferences");
            if (!prefs.getBoolean("master_sw", true)) {
                Log.i("XpMain", "Master switch is off. Abort!");
                return;
            }

            String fakeDeviceID =  prefs.getString("fake_device_id", "");
            Log.i("XpMain", "Test fakedevice id read: " + fakeDeviceID);

            try {
                HookUtils.setup(loadPackageParam.classLoader);
                NArcHook.init();

                if (NArcHook.getVersion() != NArcHook.NARCHOOK_API_VERSION) {
                    Log.e("XpMain", "API version mismatch! Expected: " + NArcHook.NARCHOOK_API_VERSION + " Got: " + NArcHook.getVersion() + ". Abort!");
                    return;
                }

                Log.i("XpMain", "Library loaded successfully.");
            } catch (Throwable e) {
                Log.e("XpMain", "Failed to load native hook library", e);
            }

            Log.i("XpMain", "Waiting for target to load to get version string");

            try {
                HookUtils.onEnter("low.moe.AppActivity#onCreate", (param, args) -> {
                    Activity target = (Activity) param.thisObject;
                    PackageInfo info = target.getApplicationContext().getPackageManager().getPackageInfo("moe.low.arc", 0);

                    int version;

                    if (info.versionName.contains("c")) {
                        version = NArcHook.ARCAEA_VERSION_CHINA;
                    } else {
                        version = NArcHook.ARCAEA_VERSION_PLAYSTORE;
                    }

                    NArcHook.enable(true);
                    NArcHook.setHookTarget(version);

                    Log.i("XpMain", "Hook enabled! Target version is: " + info.versionName);
                }, Bundle.class);

                HookUtils.onEnter("low.moe.AppActivity#logCrashlytics", (param, args) -> {
                    Log.i("XpMain", "Crashlytics report rejected: " + args[0]);

                    param.setResult(null);
                }, String.class);

                HookUtils.onEnter("com.google.firebase.FirebaseApp#initializeApp", (param, args) -> {
                    Log.i("XpMain", "Rejected firebase initialization");

                    param.setResult(null);
                }, Context.class);


            } catch (Throwable t) {
                Log.e("XpMain", "Failed to hook AppActivity", t);
            }
        }
    }
}
