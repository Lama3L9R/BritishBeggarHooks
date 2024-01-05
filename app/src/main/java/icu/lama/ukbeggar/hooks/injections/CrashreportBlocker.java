package icu.lama.ukbeggar.hooks.injections;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import icu.lama.ukbeggar.hooks.EULA;
import icu.lama.ukbeggar.hooks.HookUtils;

public class CrashreportBlocker extends CodeModifier {

    public CrashreportBlocker() {
        super(EULA.TARGET_NAME);
    }

    @Override
    public void begin(XC_LoadPackage.LoadPackageParam loadPackageParam, XSharedPreferences prefs) {
        HookUtils.onEnter("low.moe.AppActivity#logCrashlytics", (param, args) -> {
            Log.i("CrashreportBlocker", "Crashlytics report rejected: " + args[0]);

            param.setResult(null);
        }, String.class);

        HookUtils.onEnter("com.google.firebase.FirebaseApp#initializeApp", (param, args) -> {
            Log.i("CrashreportBlocker", "Rejected firebase initialization");

            param.setResult(null);
        }, Context.class);

    }
}
