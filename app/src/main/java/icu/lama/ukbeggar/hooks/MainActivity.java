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

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (EULA.iAgreeAndWillObey()) { // Please read the rules before using this software.
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
}