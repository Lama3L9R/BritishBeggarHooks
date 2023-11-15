package icu.lama.ukbeggar.hooks;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XpMain implements IXposedHookLoadPackage {
    private static final String TARGET_PACKAGE = "moe.low.arc";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (TARGET_PACKAGE.equals(loadPackageParam.packageName)) {
            try {
                System.loadLibrary("narchook");
                Log.i("XpMain", "Library loaded successfully " + NArcHook.getTargetVersion());
            } catch (Throwable e) {
                Log.e("XpMain", "Failed to load native hook library", e);
            }

            Log.i("XpMain", "Waiting for target to load to get version string");

            try {
                XposedHelpers.findAndHookMethod("low.moe.AppActivity", loadPackageParam.classLoader, "onCreate", Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
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
                    }
                });
            } catch (Throwable t) {
                Log.e("XpMain", "Failed to hook AppActivity", t);
            }
        }
    }
}
