package icu.lama.ukbeggar.hooks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RealMainActivity extends AppCompatActivity {
    public static String arcVersion = "Error";
    public static VersionType versionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!MainActivity.iWillObeyTheRules()) {
            this.finish();
        }

        NArcHook.init();
        setContentView(R.layout.settings_activity);

        final Toolbar appbar = this.findViewById(R.id.appbar);
        appbar.setTitle(R.string.app_name);


        this.findViewById(R.id.ui_nav_settings).setOnClickListener(v -> {
            final Fragment currentFrag = ((FragmentContainerView) findViewById(R.id.fragment_settings_view)).getFragment();
            if (currentFrag instanceof FragmentSettingsOptions) return;

            getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.slide_in_r, R.anim.slide_out_r)
                .replace(R.id.fragment_settings_view, new FragmentSettingsOptions())
                .commit();

            final BottomNavigationView navbar = this.findViewById(R.id.nav_view);
            navbar.setSelectedItemId(R.id.ui_nav_settings);
        });

        this.findViewById(R.id.ui_nav_dashboard).setOnClickListener(v -> {
            final Fragment currentFrag = ((FragmentContainerView) findViewById(R.id.fragment_settings_view)).getFragment();
            if (currentFrag instanceof FragmentSettingsDashboard) return;

            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(R.anim.slide_in_l, R.anim.slide_out_l)
                .replace(R.id.fragment_settings_view, new FragmentSettingsDashboard())
                .commit();

            final BottomNavigationView navbar = this.findViewById(R.id.nav_view);
            navbar.setSelectedItemId(R.id.ui_nav_dashboard);
        });
    }

}