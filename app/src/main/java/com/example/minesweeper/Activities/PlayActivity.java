package com.example.minesweeper.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.minesweeper.PlayClasses.Cell;
import com.example.minesweeper.PlayClasses.Game;
import com.example.minesweeper.PlayClasses.SoundPlayer;
import com.example.minesweeper.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PlayActivity extends AppCompatActivity {

    // Declaring the variables used:
    TextView timerText;
    @SuppressLint("StaticFieldLeak")
    public static TextView bombsText, victoryText;

    int time;
    public static int bombs;
    public static int xrange;
    public static int yrange;

    public static boolean FINISHED;
    static boolean WON = false;

    public static SoundPlayer soundPlayer;

    String filename = "minesweeper_statistics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setting Theme for the Activity:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_Minesweeper);
            Cell.darkTheme = true;
        }
        else {
            setTheme(R.style.Theme_Minesweeper);
            Cell.darkTheme = false;
        }

        // Initializing the variables for the grid:
        Game.X_RANGE = xrange;
        Game.Y_RANGE = yrange;
        Game.BOMBS = bombs;
        time = 0;
        FINISHED = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initializing the variables:
        timerText = findViewById(R.id.playTime);
        bombsText = findViewById(R.id.playBomb);
        victoryText = findViewById(R.id.playText);

        soundPlayer = new SoundPlayer(this);

        // Creating the field:
        Game.getInstance().createField(getApplicationContext());

        // Thread for running time:
        Thread timerThread = new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            time++;
                            timerText.setText(timeToString(time));
                        });
                        // Quit when the game was finished:
                        if(FINISHED)
                            Thread.currentThread().interrupt();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Starting the thread:
        timerThread.start();
    }

    // Converts int value for time to a presentable string value
    public String timeToString(int time) {
        // Calculating hours, minutes, seconds:
        int hours = time / 3600;
        int minutes = time / 60;
        int seconds = time % 60;

        if(hours != 0)
            return "| " + hours + ":" + minutes + ":" + seconds + " |";
        if(seconds < 10)
            return "|  " + minutes + ":0" + seconds + " |";
        return "| " + minutes + ":" + seconds + " |";

    }

    // Method sets victoryText text and plays the sound.
    // It is called from either from onBackBtnClick or Game class onGameLost and OnGameWon methods.
    @SuppressLint("SetTextI18n")
    public static void onFinish(boolean won, boolean fromBack){
        WON = won;
        if(won) {
            victoryText.setText("You won! Congratulations!");
            //pt
            if(!SettingsActivity.MUTED && !fromBack) soundPlayer.playVictorySound();
        }
        else if(!fromBack){
            victoryText.setText("You lost. Better luck next time!");
            if(!SettingsActivity.MUTED) soundPlayer.playBombSound();
        }
        FINISHED = true;
    }

    // Method finishing activity if Back button is clicked:
    public void OnBackBtnClick(View view) {
        onFinish(WON, true);
        super.onBackPressed();
    }

    // Method recreating activity if Restart button is clicked:
    public void OnReBtnClick(View view) {
        Activity activity = this;
        activity.recreate();
    }

    // Writes given string to file in appending mode:
    private void writeFile(String toWrite) {
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_APPEND);
            fos.write(toWrite.getBytes());
            fos.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Writes to file a line if player won the game:
        if(isFinishing() && WON) {
            String line = "You won the game after " + timerText.getText().toString() + " in " + xrange + "x" + yrange + "grid.\n";
            writeFile(line);
        }
    }
}
