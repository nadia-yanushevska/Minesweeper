package com.example.minesweeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.minesweeper.R;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting Theme for the Activity:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme_Minesweeper);
        else setTheme(R.style.Theme_Minesweeper);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

    }


    // Methods directing to a play activity with different field sizes and number of bombs depending on which button was clicked:

    public void OnEasyBtnClick(View view) {
        Intent toPlayIntent = new Intent(this, PlayActivity.class);
        startActivity(toPlayIntent);

        PlayActivity.yrange = 9;
        PlayActivity.xrange = 9;
        PlayActivity.bombs = 10;
    }

    public void OnMediumBtnClick(View view) {
        Intent toPlayIntent = new Intent(this, PlayActivity.class);
        startActivity(toPlayIntent);

        PlayActivity.yrange = 12;
        PlayActivity.xrange = 12;
        PlayActivity.bombs = 20;
    }

    public void OnHardBtnClick(View view) {
        Intent toPlayIntent = new Intent(this, PlayActivity.class);
        startActivity(toPlayIntent);

        PlayActivity.yrange = 16;
        PlayActivity.xrange = 16;
        PlayActivity.bombs = 40;
    }


    // Method finishing activity if Back button is clicked:
    public void OnBackBtnClick(View view) {
        finish();
    }
}