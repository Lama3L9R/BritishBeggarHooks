package icu.lama.ukbeggar.hooks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import icu.lama.narchook.CURLHacks;
import icu.lama.narchook.FakeDeviceID;
import icu.lama.narchook.NArcHook;
import icu.lama.ukbeggar.hooks.injections.CodeModifier;

public class XpMain implements IXposedHookLoadPackage {

    private static final List<CodeModifier> INJECTIONS = Arrays.asList(
            new icu.lama.ukbeggar.hooks.injections.NativeInjection(),
            new icu.lama.ukbeggar.hooks.injections.CrashreportBlocker(),
            new icu.lama.ukbeggar.hooks.injections.XposedCheck()
    );

    private static final XSharedPreferences prefs = new XSharedPreferences("icu.lama.ukbeggar.hooks");

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!EULA.iAgreeAndWillObey()) {
            return;
        }

        if (!prefs.getBoolean("master_sw", true)) {
            Log.i("XpMain", "Master switch is off. Abort!");
            return;
        }

        for (CodeModifier injection : INJECTIONS) {
            if (loadPackageParam.packageName.equals(injection.packageName)) {
                HookUtils.setup(loadPackageParam.classLoader);
                injection.begin(loadPackageParam, prefs);
            }
        }
    }

}
