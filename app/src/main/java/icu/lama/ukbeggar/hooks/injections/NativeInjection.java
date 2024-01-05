package icu.lama.ukbeggar.hooks.injections;

import android.util.Log;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import icu.lama.narchook.CURLHacks;
import icu.lama.narchook.FakeDeviceID;
import icu.lama.narchook.NArcHook;
import icu.lama.ukbeggar.hooks.EULA;

public class NativeInjection extends CodeModifier {

    public NativeInjection() {
        super(EULA.TARGET_NAME);
    }

    @Override
    public void begin(XC_LoadPackage.LoadPackageParam loadPackageParam, XSharedPreferences prefs) {
        try {
            if (!NArcHook.init()) {
                Log.e("NativeInjection", "Failed to initialize native hook library");
                return;
            }

            if (prefs.getBoolean("disable_ssl_pinning", false)) {
                Log.i("NativeInjection", "Disabling SSL pinning");
                CURLHacks.toggleFeature(true);
                CURLHacks.toggleDisableSSLPinning(true);
            }

            if (prefs.getBoolean("enable_fake_device_id", false)) {
                Log.i("NativeInjection", "Enabling fake device id");

                String fakeDeviceIDValue = prefs.getString("fake_device_id", "");
                if (fakeDeviceIDValue.isEmpty()) {
                    Log.e("NativeInjection", "Fake device id is empty! Generating a random one.");

                    FakeDeviceID.prepareRandom((int) (((int) (System.currentTimeMillis() ^ 0xCA0ADA) * (Math.round(Math.random() * 100))) >> 14));
                    fakeDeviceIDValue = FakeDeviceID.randomDeviceID();

                    Log.i("NativeInjection", "Generated fake device id: " + fakeDeviceIDValue);
                }

                FakeDeviceID.setFakeDeviceID(fakeDeviceIDValue);
                FakeDeviceID.toggleFeature(true);
            }

            if (prefs.getBoolean("enable_custom_api", false)) {
                 String apiv2 = prefs.getString("custom_api_v2", "");
                 String apiLegacy = prefs.getString("custom_api", apiv2);

                 if (!apiv2.isEmpty()) {
                    CURLHacks.setCustomAPI(apiLegacy);
                    CURLHacks.setCustomAPIV2(apiv2);
                 } else {
                     Log.e("NativeInjection", "Custom API v2 is empty! Disabling custom API.");
                 }
            }

            if (prefs.getBoolean("approve_all_certificates", false)) {
                Log.i("NativeInjection", "Approving all certificates");
                CURLHacks.approveAllCertificate(true);
            }

            Log.i("NativeInjection", "Library loaded successfully.");
        } catch (Throwable e) {
            Log.e("NativeInjection", "Failed to load native hook library", e);
        }
    }
}
