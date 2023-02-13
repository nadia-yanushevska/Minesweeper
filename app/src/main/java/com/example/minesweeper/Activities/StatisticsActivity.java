package com.example.minesweeper.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minesweeper.MainActivity;
import com.example.minesweeper.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class StatisticsActivity extends AppCompatActivity {

    // Declaring the variables used:
    ListView statList;
    String[] statArray = new String[1];
    String filename = "minesweeper_statistics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting Theme for the Activity:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkTheme_Minesweeper);
        else setTheme(R.style.Theme_Minesweeper);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Initializing the list:
        statList = findViewById(R.id.statList);

        // Changing theme if needed:
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            changeTheme();
        }

        // Setting default value
        statArray[0] = "No statistics available.";

        // Reading file:
        readFile();

        // Sorting array:
        sortArray();

        // Setting list items:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statArray);
        statList.setAdapter(adapter);
    }

    // Changes theme of the list:
    public void changeTheme() {
        statList.setBackground(getResources().getDrawable(R.color.blue_medium));
        statList.setDivider(getResources().getDrawable(R.color.blue_medium));
        statList.setDividerHeight(10);
    }

    // Checks if file exists
    public boolean fileExists(String fn) {
        File file = getBaseContext().getFileStreamPath(fn);
        return file.exists();
    }

    // Reads the file setting every line to be array item:
    private void readFile() {
        if (fileExists(filename)) {

            FileInputStream fis = null;
            try {
                fis = openFileInput(filename);
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String fLine;
            String[] statLines = new String[10];
            int count = 0;

            try {
                while ((fLine = br.readLine()) != null) {
                    statLines[count] = fLine;
                    count++;
                    if(count == statLines.length)
                        statLines = Arrays.copyOf(statLines, count+10);
                }
                statArray = Arrays.copyOf(statLines, count);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    // Sorts the array by level difficulty:
    private void sortArray() {
        if(statArray.length < 2)
            return;
        for(int i = 0; i < statArray.length-1; i++)
            for(int j = i+1; j < statArray.length; j++) {
                if(statArray[i] != null && statArray[j] != null) {
                    int indexi = statArray[i].indexOf('x');
                    int indexj = statArray[j].indexOf('x');
                    int xvaluei = statArray[i].charAt(indexi - 1) - 48;
                    int xvaluej = statArray[j].charAt(indexj - 1) - 48;
                    if(xvaluei == 9)
                        xvaluei = 1;
                    if(xvaluej == 9)
                        xvaluej = 1;
                    if(xvaluei > xvaluej)
                        switchArrayElements(i, j);
                }
            }
    }

    // Switches two array elements:
    private void switchArrayElements(int i, int j) {
        String temp = statArray[i];
        statArray[i] = statArray[j];
        statArray[j] = temp;
    }

    // Method finishing activity if Back button is clicked:
    public void OnBackBtnClick(View view) {
        finish();
    }


    // Save and restore instance states handling changes in screen orientation:
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Saving the values
        outState.putStringArray("statistics", statArray);

        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        // Restoring the values
        statArray = outState.getStringArray("statistics");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statArray);
        statList.setAdapter(adapter);
    }
}