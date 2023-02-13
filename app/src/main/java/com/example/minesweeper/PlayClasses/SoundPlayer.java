package com.example.minesweeper.PlayClasses;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.minesweeper.R;

public class SoundPlayer {

    // Declaring the variables:
    private static SoundPool soundPool;
    private static int bombSound;
    private static int victorySound;

    // Setting SoundPlayer:
    public SoundPlayer(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        bombSound = soundPool.load(context, R.raw.bomb_sound, 1);
        victorySound = soundPool.load(context, R.raw.victory_sound, 1);
    }

    // Plays the bomb sound:
    public void playBombSound() {
        soundPool.play(bombSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    // Plays the victory music:
    public void playVictorySound() {
        soundPool.play(victorySound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
