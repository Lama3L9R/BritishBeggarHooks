package icu.lama.ukbeggar.hooks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import icu.lama.ukbeggar.hooks.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (iWillObeyTheRules()) { // Please read the rules before using this software.
            Intent myIntent = new Intent(MainActivity.this, RealMainActivity.class);
            this.finish();
            MainActivity.this.startActivity(myIntent);

            return;
        }

        // open a dialog box to tell the user that the rules are not obeyed. Use the text from @string/door_open_hint


        new AlertDialog.Builder(MainActivity.this)
                .setTitle("The gate shall not be opened.")
                .setMessage(R.string.door_open_hint)
                .setPositiveButton("ACK", (di, id) -> { }).show();
    }
    public boolean iWillObeyTheRules() {
        final String[] RULES = new String[] {
                "You MUST obey the following rules to use this software:",

                "1. You are NOT allowed to make any profit with this software",
                "2. You acknowledge that this software has no relation to Lowiro. Any keyword related to Arcaea is coincidental.",
                "3. You acknowledge that this software has no relations to Arcaea. Any keyword related to Arcaea is coincidental.",
                "4. You are not allowed to spread a modified version of this software in any case.",
                "  a) Modified version means the source code is not the same as the source code published by the author",
                "  b) If your modification is not related to MainActivity, you are allowed to publish a modified version",
                "     but only the source code.",
                "  c) You shall not publish any version of compiled binaries/APK files/production builds.",
                "5. You are NOT allowed to publish any tutorials if the following conditions are matched: ",
                "  a) Your tutorial is guiding the reader/viewer to modify the source code.",
                "  b) Your tutorial is guiding the reader/viewer to reverse engineering this application",
                "  c) Your tutorial is guiding the reader/viewer to violate the rules",
                "6. You acknowledge the library 'libnarchook.so' is closed source, and you are not allowed to reverse engineering.",
                "7. THE CONSEQUENCES CAUSED BY THE USE OF THIS SOFTWARE SHALL BE BORNE BY THE USER.",
                "  a) THE CONSEQUENCES may include but not limited to:",
                "      i) Your game account get banned",
                "     ii) Your device get banned",
                "    iii) Your device get bricked",
                "     iv) Any kind of data lost",


                "Any violation of the rules will cause the author of this project to suspend updates."
        };

        return false;
    }
}