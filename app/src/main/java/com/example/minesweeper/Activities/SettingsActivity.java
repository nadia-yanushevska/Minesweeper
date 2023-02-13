package com.example.minesweeper.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.minesweeper.R;

public class SettingsActivity extends AppCompatActivity {

    // Declaring the variable used:
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch themeSwitch, soundSwitch;

    boolean cancelByDefault = true;
    boolean darkTheme = false;
    public static boolean MUTED = false;

    int themeChanged = 0, soundChanged = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting Theme for the Activity:
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_Minesweeper);
            darkTheme = true;
        } else {
            setTheme(R.style.Theme_Minesweeper);
            darkTheme = false;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initializing the switches:
        themeSwitch = findViewById(R.id.settNightSwitch);
        soundSwitch = findViewById(R.id.settMuteSwitch);

        // Setting the values of switches and changing theme if needed:
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch.setChecked(true);
            changeTheme();
        }
        if(MUTED)
            soundSwitch.setChecked(true);

        // Listener checking for change in the values of switches:
        themeSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            themeChanged++;
        });
        soundSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            MUTED = isChecked;
            soundChanged++;
        });

    }

    // Changes theme of the switches:
    @SuppressLint("UseCompatLoadingForDrawables")
    public void changeTheme() {
        themeSwitch.setBackground(getResources().getDrawable(R.color.blue_medium));
        themeSwitch.setTextColor(getResources().getColor(R.color.beige_light));
        soundSwitch.setBackground(getResources().getDrawable(R.color.blue_medium));
        soundSwitch.setTextColor(getResources().getColor(R.color.beige_light));
    }

    // Method finishing activity if Back button is clicked:
    public void OnCancelBtnClick(View view) {
        finish();
    }

    // Method finishing activity if Apply button is clicked:
    public void OnApplyBtnClick(View view) {
        cancelByDefault = false;
        finish();
    }

    @Override
    protected void onDestroy() {

        // Changes the values of switches to the ones that were when first entering the activity
        // (in case cancel or back buttons were clicked):
        if(isFinishing() && cancelByDefault) {

            if(themeChanged%2 == 1) {
                themeSwitch.setChecked(!themeSwitch.isChecked());
            }

            if(soundChanged%2 == 1) {
                soundSwitch.setChecked(!soundSwitch.isChecked());
            }

        }

        super.onDestroy();
    }
}