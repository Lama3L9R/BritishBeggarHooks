package icu.lama.ukbeggar.hooks;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class FragmentSettingsOptions extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }
}