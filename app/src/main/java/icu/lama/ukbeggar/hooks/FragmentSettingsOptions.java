package icu.lama.ukbeggar.hooks;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class FragmentSettingsOptions extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}