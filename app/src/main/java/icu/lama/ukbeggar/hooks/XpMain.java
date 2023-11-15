package icu.lama.ukbeggar.hooks;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XpMain implements IXposedHookLoadPackage {
    private static final String TARGET_PACKAGE = "moe.low.arc";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (TARGET_PACKAGE.equals(loadPackageParam.packageName) && MainActivity.iWillObeyTheRules()) {
            try {
                System.loadLibrary("narchook");
            } catch (Throwable e) {
                Log.e("XpMain", "Failed to load native hook library", e);
            }
        }
    }
}
