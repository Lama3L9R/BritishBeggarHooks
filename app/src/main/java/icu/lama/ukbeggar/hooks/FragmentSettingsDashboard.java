package icu.lama.ukbeggar.hooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentSettingsDashboard extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final SharedPreferences prefs = getActivity().getSharedPreferences("icu.lama.ukbeggar.hooks_preferences", Context.MODE_PRIVATE);
        try {
            PackageInfo verInfo = getActivity().getPackageManager().getPackageInfo("moe.low.arc", 0);

            RealMainActivity.versionType = verInfo.versionName.contains("c") ? VersionType.CHINA : VersionType.PLAYSTORE;
            RealMainActivity.arcVersion = verInfo.versionName.replace("c", "");
        } catch (PackageManager.NameNotFoundException e) {

            final TextView failed = getView().findViewById(R.id.versionFailed);
            failed.setVisibility(View.VISIBLE);
        }

        final String supportedVer = NArcHook.getTargetVersion();

        final TextView loading = getView().findViewById(R.id.loadingVersion);
        loading.setVisibility(View.INVISIBLE);

        final TextView failed;
        if (NArcHook.isFeatureSupported(NArcHook.FEAT_HOOK_SEARCHING)) {
            failed = getView().findViewById(R.id.versionOK);
            failed.setText(R.string.generic_hooking_enabled);
        } else {
            if (supportedVer.equals(RealMainActivity.arcVersion)) {
                failed = getView().findViewById(R.id.versionOK);
            } else {
                failed = getView().findViewById(R.id.versionFailed);
            }
        }
        failed.setVisibility(View.VISIBLE);

        final TextView versionDisplay = getView().findViewById(R.id.versionStringDisplay);
        versionDisplay.setText(getString(R.string.arc_read_version,
                RealMainActivity.arcVersion,
                (RealMainActivity.versionType == VersionType.CHINA ?
                        getString(R.string.arc_version_china) :
                        getString(R.string.arc_version_playstore))
        ));

        final FloatingActionButton btnToggle = getView().findViewById(R.id.enableButton);

        btnToggle.setOnClickListener(it -> {
            boolean newState = !prefs.getBoolean("mainSwitch", false);
            prefs.edit().putBoolean("mainSwitch", newState).apply();

            if (newState) {
                btnToggle.setImageResource(R.drawable.disable);
            } else {
                btnToggle.setImageResource(R.drawable.enable);
            }
        });
    }
}