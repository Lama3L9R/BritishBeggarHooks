package icu.lama.ukbeggar.hooks.injections;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import icu.lama.ukbeggar.hooks.HookUtils;

public class XposedCheck extends CodeModifier {

    public XposedCheck() {
        super("icu.lama.ukbeggar.hooks");
    }

    @Override
    public void begin(XC_LoadPackage.LoadPackageParam loadPackageParam, XSharedPreferences prefs) {
        HookUtils.onEnter("icu.lama.ukbeggar.hooks.FragmentSettingsDashboard#lsposedExists", (param, args) -> {
            param.setResult(true);
        });
    }
}
