package com.example.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.minesweeper.Activities.InstructionsActivity;
import com.example.minesweeper.Activities.LevelsActivity;
import com.example.minesweeper.Activities.SettingsActivity;
import com.example.minesweeper.Activities.StatisticsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setting Theme for the Activity:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme_Minesweeper);
        else setTheme(R.style.Theme_Minesweeper);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // Methods directing to a different activity depending on which button was clicked:

    public void OnPlayBtnClick(View view) {
        Intent toLevelsIntent = new Intent(this, LevelsActivity.class);
        startActivity(toLevelsIntent);
    }

    public void OnStatBtnClick(View view) {
        Intent toStatIntent = new Intent(this, StatisticsActivity.class);
        startActivity(toStatIntent);
    }

    public void OnInstBtnClick(View view) {
        Intent toInstIntent = new Intent(this, InstructionsActivity.class);
        startActivity(toInstIntent);
    }

    public void OnSettBtnClick(View view) {
        Intent toSettIntent = new Intent(this, SettingsActivity.class);
        startActivity(toSettIntent);
    }
}