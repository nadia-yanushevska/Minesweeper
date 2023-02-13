package com.example.minesweeper.PlayClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.minesweeper.Activities.PlayActivity;
import com.example.minesweeper.R;

public class Cell extends View implements View.OnClickListener , View.OnLongClickListener {

    // Declaring the variables:
    private int value = 0;
    private boolean isBomb;
    private boolean isOpen;
    private boolean isClicked;
    private boolean isMarked;
    public static boolean darkTheme = false;

    private int x , y;
    private int position;

    // Cell constructor:
    public Cell(Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


    // OnClick methods for Cells:
    @Override
    public void onClick(View v) {
        Game.getInstance().click( getXPosition(), getYPosition() );
        PlayActivity.bombsText.setText("| " + (Game.BOMBS-Game.MARKED_CELLS) + " out of " + Game.BOMBS +" |");
    }

    @Override
    public boolean onLongClick(View v) {
        Game.getInstance().mark( getXPosition() , getYPosition() );

        PlayActivity.bombsText.setText("| " + (Game.BOMBS-Game.MARKED_CELLS) + " out of " + Game.BOMBS +" |");

        return true;
    }


    // Drawing Cells:
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Minesweeper" , "Cell::onDraw");
        drawCell(canvas);

        if( isMarked() ){
            drawFlag(canvas);
        }else if( isOpen() && isBomb() && !isClicked() ){
            drawBomb(canvas);
        }else {
            if( isClicked() ){
                if( getValue() == -1 ){
                    drawActiveBomb(canvas);
                }else {
                    drawOpenCell(canvas);
                }
            }else{
                drawCell(canvas);
            }
        }
    }


    // Setting drawables (different for night and day themes and drawing cells
    private void drawActiveBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.active_bomb_cell);
        if(darkTheme)
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.active_bomb_cell_dark);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flagged_cell);
        if(darkTheme)
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.flagged_cell_dark);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawCell(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.closed_cell);
        if(darkTheme)
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.closed_cell_dark);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_cell);
        if(darkTheme)
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_cell_dark);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawOpenCell( Canvas canvas ){
        Drawable drawable = null;

        switch (getValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_dark);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_1);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_1_dark);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_2);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_2_dark);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_3);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_3_dark);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_4);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_4_dark);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_5);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_5_dark);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_6);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_6_dark);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_7);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_7_dark);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_8);
                if(darkTheme)
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.opened_cell_8_dark);
                break;
        }

        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }


    // Dets and sets method for the variables
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        isBomb = false;
        isOpen = false;
        isClicked = false;
        isMarked = false;

        if( value == -1 ){
            isBomb = true;
        }

        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        isOpen = true;
        invalidate();
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked() {
        this.isClicked = true;
        this.isOpen = true;

        invalidate();
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public int getXPosition() {
        return x;
    }

    public int getYPosition() {
        return y;
    }

    public void setPosition( int x , int y ){
        this.x = x;
        this.y = y;

        this.position = y * Game.X_RANGE + x;

        invalidate();
    }
}