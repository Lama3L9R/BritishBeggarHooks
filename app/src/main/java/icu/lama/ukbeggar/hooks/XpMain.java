package icu.lama.ukbeggar.hooks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import icu.lama.narchook.CURLHacks;
import icu.lama.narchook.FakeDeviceID;
import icu.lama.narchook.NArcHook;

public class XpMain implements IXposedHookLoadPackage {
    private static final String TARGET_PACKAGE = "moe.low.arc";
    private static final String SELF_PACKAGE = "icu.lama.ukbeggar.hooks";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (TARGET_PACKAGE.equals(loadPackageParam.packageName)) {
            XSharedPreferences prefs = new XSharedPreferences("icu.lama.ukbeggar.hooks");
            if (!prefs.getBoolean("master_sw", true)) {
                Log.i("XpMain", "Master switch is off. Abort!");
                return;
            }

            try {
                HookUtils.setup(loadPackageParam.classLoader);
                NArcHook.init();

                if (prefs.getBoolean("disable_ssl_pinning", false)) {
                    Log.i("XpMain", "Disabling SSL pinning");
                    CURLHacks.toggleFeature(true);
                }

                if (prefs.getBoolean("enable_fake_device_id", false)) {
                    Log.i("XpMain", "Enabling fake device id");

                    String fakeDeviceIDValue = prefs.getString("fake_device_id", "");
                    if (fakeDeviceIDValue.isEmpty()) {
                        Log.e("XpMain", "Fake device id is empty! Generating a random one.");

                        FakeDeviceID.prepareRandom((int) (((int) (System.currentTimeMillis() ^ 0xCA0ADA) * (Math.round(Math.random() * 100))) >> 14));
                        fakeDeviceIDValue = FakeDeviceID.randomDeviceID();
                        prefs.edit().putString("fake_device_id", fakeDeviceIDValue).apply();

                        Log.i("XpMain", "Generated fake device id: " + fakeDeviceIDValue);
                    }

                    FakeDeviceID.setFakeDeviceID(fakeDeviceIDValue);
                    FakeDeviceID.toggleFeature(true);
                }

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

                    CURLHacks.toggleFeature(true);
                    FakeDeviceID.toggleFeature(true);

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

        if (SELF_PACKAGE.equals(loadPackageParam.packageName)) {
            HookUtils.setup(loadPackageParam.classLoader);

            HookUtils.onEnter("icu.lama.ukbeggar.hooks.FragmentSettingsDashboard#lsposedExists", (param, args) -> {
                param.setResult(true);
            });
        }
    }
}
