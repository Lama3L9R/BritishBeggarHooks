package icu.lama.ukbeggar.hooks;


import de.robv.android.xposed.XSharedPreferences;

public class XpConfig {
    private static XSharedPreferences conf = new XSharedPreferences("icu.lama.ukbeggar.hooks", "config");

    static String getCustomAPIV2Address() {
        if (conf.hasFileChanged()) {
            conf.reload();
        }

        return conf.getString("hookApiV2", "");
    }

    static void setCustomAPIV2Address(String url) {
        // set preference
        conf.edit().putString("hookApiV2", url).apply();
    }

    static boolean shouldHookAPIV2() {
        if (conf.hasFileChanged()) {
            conf.reload();
        }

        return conf.getString("hookApiV2", "").isEmpty();
    }

    static boolean shouldHookSSL() {
        if (conf.hasFileChanged()) {
            conf.reload();
        }

        return conf.getBoolean("mainSwitch", false);
    }

    static void setHookSSL(boolean hook) {
        conf.edit().putBoolean("mainSwitch", hook).apply();
    }
}
