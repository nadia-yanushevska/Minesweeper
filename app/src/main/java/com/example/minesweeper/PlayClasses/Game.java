package com.example.minesweeper.PlayClasses;

import android.content.Context;

import com.example.minesweeper.Activities.PlayActivity;

import java.util.Random;

public class Game {

    // Declaring the variables:

    public static Game instance;

    public static int BOMBS = PlayActivity.bombs;
    public static int X_RANGE = PlayActivity.xrange;
    public static int Y_RANGE = PlayActivity.yrange;
    public static int OPEN_CELLS, MARKED_CELLS = 0;

    private Cell[][] GameField = new Cell[16][16];


    // Implementing Singleton pattern:
    public static Game getInstance() {
        if( instance == null ){
            instance = new Game();
        }
        return instance;
    }

    public Game() {}

    // Generates a field and assigns the values to GameField 2D array:
    public void createField(Context context){

        int[][] GeneratedField = fieldGenerator(BOMBS, X_RANGE, Y_RANGE);

        OPEN_CELLS = 0;
        MARKED_CELLS = 0;

        for( int x = 0 ; x < X_RANGE ; x++ ){
            for( int y = 0 ; y < Game.Y_RANGE ; y++ ){
                if( GameField[x][y] == null ){
                    GameField[x][y] = new Cell( context , x,y);
                }
                GameField[x][y].setValue(GeneratedField[x][y]);
                GameField[x][y].invalidate();
            }
        }
    }

    // Generates bombs' cells in a field by assigning value of -1 and
    // calculates other cells ( 0 - empty cell, 1,2,3,4,5,6,7,8 - display number of bombs in neighbouring cells):
    private static int[][] fieldGenerator( int bombs , final int xRange , final int yRange){
        Random random = new Random();

        int [][] field = new int[xRange][yRange];

        while( bombs > 0 ){
            int x = random.nextInt(xRange);
            int y = random.nextInt(yRange);

            if( field[x][y] != -1 ){
                field[x][y] = -1;
                bombs--;
            }
        }
        field = calculateNeighbours(field, xRange, yRange);

        return field;
    }

    // Calculating all the cells value other, except for cells containing bombs:
    private static int[][] calculateNeighbours( int[][] field , final int xRange , final int yRange){
        for( int x = 0 ; x < xRange ; x++){
            for( int y = 0 ; y < yRange ; y++){
                if( field[x][y] == -1 ){
                    continue;
                }
                int bombCount = 0;

                if(hasBombAt(field,x - 1 ,y    ,xRange,yRange)) bombCount++; // left
                if(hasBombAt(field,x + 1 ,y    ,xRange,yRange)) bombCount++; // right
                if(hasBombAt(field,x     ,y - 1,xRange,yRange)) bombCount++; // top
                if(hasBombAt(field,x     ,y + 1,xRange,yRange)) bombCount++; // bottom

                if(hasBombAt(field,x - 1 ,y - 1,xRange,yRange)) bombCount++; // top-left
                if(hasBombAt(field,x - 1 ,y + 1,xRange,yRange)) bombCount++; // bottom-left
                if(hasBombAt(field,x + 1 ,y - 1,xRange,yRange)) bombCount++; // top-right
                if(hasBombAt(field,x + 1 ,y + 1,xRange,yRange)) bombCount++; // bottom-right

                field[x][y] = bombCount;
            }
        }

        return field;
    }

    // Checks whether the cell with given position is out of range or has the bomb:
    private static boolean hasBombAt( final int [][] field, final int x , final int y , final int xRange , final int yRange){
        if(x >= 0 && y >= 0 && x < xRange && y < yRange && field[x][y] == -1) return true;
        return false;
    }

    // Returns cell with given position:
    public Cell getCellAt(int position) {
        return GameField[position % Game.X_RANGE][position / Game.X_RANGE];
    }

    // Opens cell with given position, if empty opens other neighbouring empty cells, until number is reached;
    // and checks if the player lost or won the game:
    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < Game.X_RANGE && y < Game.Y_RANGE && !GameField[x][y].isClicked() && !GameField[x][y].isOpen()){
            GameField[x][y].setClicked();
            OPEN_CELLS++;

            if( GameField[x][y].getValue() == 0 ){
                for( int r = -1 ; r <= 1 ; r++ ){
                    for( int c = -1 ; c <= 1 ; c++){
                        click(x + r , y + c);
                    }
                }
            }

            if( GameField[x][y].isBomb() ){
                onGameLost();
            }
            else if(OPEN_CELLS == Game.X_RANGE*Game.Y_RANGE - Game.BOMBS) {
                onGameWon();
            }
        }
    }

    // Either adds a flag to a cell or removes it:
    public void mark( int x , int y ){
        boolean isMarked = GameField[x][y].isMarked();

        if(isMarked)
            MARKED_CELLS--;
        else MARKED_CELLS++;

        GameField[x][y].setMarked(!isMarked);
        GameField[x][y].invalidate();
    }

    // Is invoked when game is lost; shows where the bombs were hidden, calls onFinish method in PlayActivity:
    private void onGameLost(){
        for ( int x = 0 ; x < Game.X_RANGE ; x++ ) {
            for (int y = 0; y < Game.Y_RANGE; y++) {
                GameField[x][y].setOpen();
            }
        }
        PlayActivity.onFinish(false, false);
    }

    // Is invoked when game is won; calls onFinish method in PlayActivity:
    private void onGameWon(){
        PlayActivity.bombsText.setText("| 0 out of " + Game.BOMBS + " |");
        PlayActivity.onFinish(true, false);
    }
}
