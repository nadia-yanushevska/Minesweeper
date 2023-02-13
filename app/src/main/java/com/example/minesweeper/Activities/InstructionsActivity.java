package com.example.minesweeper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.minesweeper.MainActivity;
import com.example.minesweeper.R;

public class InstructionsActivity extends AppCompatActivity {

    // Declaring the variables used:
    TextView instView;
    String[] instructions = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting Theme for the Activity:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme_Minesweeper);
        else setTheme(R.style.Theme_Minesweeper);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        // Initializing the instView variable:
        instView = findViewById(R.id.instView);
        // Change theme of it:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            changeTheme();
        }

        // Setting text for the instructions array items:
        instructions[0] = "You are presented with the field of cells." +
                "You can choose which size the field will be: either 9x9, 12x12 or 16x16, by choosing the level.";
        instructions[1] = "The easy level is 9x9 field with 10 bombs hidden.";
        instructions[2] = "The medium level is 12x12 field with 20 bombs hidden.";
        instructions[3] = "The hard level is 16x16 field with 40 bombs hidden.";
        instructions[4] = "The purpose of the game is to open all the cells on the field not containing a bomb. " +
                "You lose if you set off a bomb cell.";
        instructions[5] = "Every non-bomb cell you open will tell you the total number of bombs in the eight neighboring cells. " +
                "If you open an empty cell, the neighboring empty cells will open until the numbered cell is reached.";
        instructions[6] = "Once you are sure that a particular cell contains a bomb, flag it by long-tapping the cell. " +
                        "Once you have flagged all the bombs around an open cell, you can safely open the remaining neighboring cells.";
        instructions[7] = "To start a new game, click on the button in the top-right corner. To exit, click on the button in the top-left corner.";
        instructions[8] = "Screen orientation is locked in the game. To switch it, exit to menu, rotate your phone and go back to play the game.";
        instructions[9] = "The game uses some sound effects. If it's an undesirable feature, you can mute the application in Settings. " +
                "You can also change to night mode. Do not forget to apply the changes.";

        // Adding text and scroll movement for instView variable:
        instView.setText(arrayToString());
        instView.setMovementMethod(new ScrollingMovementMethod());

    }

    // Changes theme of the instView:
    public void changeTheme() {
        instView.setBackground(getResources().getDrawable(R.color.blue_medium));
        instView.setTextColor(getResources().getColor(R.color.beige_light));
    }

    // Formatting array:
    public String arrayToString() {
        String result = instructions[0];

        for(int i = 1; i < instructions.length; i++)
            result += "\n" + instructions[i];

        return result;
    }

    // Method finishing activity if Back button is clicked:
    public void OnBackBtnClick(View view) {
        finish();
    }

    // Save and restore instance states handling changes in screen orientation:
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Saving the values
        outState.putString("instructions", instView.getText().toString());

        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        // Restoring the values
        instView.setText(outState.getString("instructions"));
    }

}