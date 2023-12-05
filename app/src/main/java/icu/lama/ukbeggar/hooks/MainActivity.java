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

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("The gate shall not be opened.")
                .setMessage(R.string.door_open_hint)
                .setPositiveButton("ACK", (di, id) -> { }).show();
    }
    public static boolean iWillObeyTheRules() {
        final String[] RULES = new String[] {
                "You MUST obey the following rules to use this software:",
                "",
                "1. You are NOT allowed to make any profit with this software.",
                "2. You acknowledge that this software has no relations to Lowiro. Any keyword related to Lowiro is a coincident.",
                "3. You acknowledge that this software has no relations to Arcaea. Any keyword related to Arcaea is a coincident.",
                "4. You are not allowed to spread a modified version of this software if the following conditions are matched: ",
                "  a) Your modification is related to MainActivity.",
                "  b) You are publishing anything else than the source code.",
                "  c) The only exception is the original author of this project, lamadaemon, grants you the permission to do so.",
                "5. You are NOT allowed to publish any tutorials if the following conditions are matched: ",
                "  a) Your tutorial is guiding the reader/viewer to modify the source code.",
                "  b) Your tutorial is guiding the reader/viewer to reverse engineering this application",
                "  c) Your tutorial is guiding the reader/viewer to violate the rules",
                "  d) The only exception is the original author of this project, lamadaemon, grants you the permission to do so.",
                "6. You acknowledge the library 'libnarchook.so' is closed source, and you are not allowed to reverse engineering.",
                "   Specifically but not limited to these following actions are prohibited:",
                "  a) Decompile",
                "  b) Modify",
                "  c) Republish modified version nor the original version",
                "7. THE CONSEQUENCES CAUSED BY THE USE OF THIS SOFTWARE SHALL BE BORNE BY THE USER.",
                "  a) THE CONSEQUENCES may include but not limited to:",
                "      i) Your game account get banned.",
                "     ii) Your house get burned.",
                "    iii) The third world war.",
                "     iv) The god decided to punish people and you are the first one.",
                "",
                "",
                "Any violation of the rules will cause the author of this project to suspend updates."
        };

        final String HINT_A = "Make this function return true to represent that you have read the rules and will obey them.";
        final String HINT_B = "If you are using smali editor, make this function return 1 (1 means true)";
        return true;
    }
}