package icu.lama.ukbeggar.hooks.injections;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class CodeModifier {
    public String packageName;

    public CodeModifier(String packageName) {
        this.packageName = packageName;
    }

    public abstract void begin(XC_LoadPackage.LoadPackageParam loadPackageParam, XSharedPreferences prefs);
}
