package icu.lama.ukbeggar.hooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class RealMainActivity extends AppCompatActivity {
    public static String supportedVersion = "5.1.3";

    public static String arcVersion = "Error";
    public static VersionType versionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        final SharedPreferences prefs = getSharedPreferences("icu.lama.ukbeggar.hooks_preferences", Context.MODE_PRIVATE);

        try {
            PackageInfo verInfo = this.getPackageManager().getPackageInfo("moe.low.arc", 0);

            versionType = verInfo.versionName.contains("c") ? VersionType.CHINA : VersionType.PLAYSTORE;
            arcVersion = verInfo.versionName.replace("c", "");
        } catch (PackageManager.NameNotFoundException e) {
            final TextView failed = this.findViewById(R.id.versionFailed);
            failed.setVisibility(View.VISIBLE);
        }

        final TextView loading = this.findViewById(R.id.loadingVersion);
        loading.setVisibility(View.INVISIBLE);

        final TextView failed;
        if (supportedVersion.equals(arcVersion)) {
            failed = this.findViewById(R.id.versionOK);
        } else {
            failed = this.findViewById(R.id.versionFailed);
        }
        failed.setVisibility(View.VISIBLE);

        final TextView versionDisplay = this.findViewById(R.id.versionStringDisplay);
        versionDisplay.setText(arcVersion + (versionType == VersionType.PLAYSTORE ? " Playstore" : ""));

        final FloatingActionButton btnToggle = this.findViewById(R.id.enableButton);
        btnToggle.setOnClickListener(it -> {
            boolean newState = !prefs.getBoolean("mainSwitch", false);
            prefs.edit().putBoolean("mainSwitch", newState).apply();

            if (newState) {
                btnToggle.setImageResource(R.drawable.enable);
            } else {
                btnToggle.setImageResource(R.drawable.disable);
            }
        });

    }



}