package com.example.minesweeper.PlayClasses;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class Field extends GridView {

    // Creating the field
    public Field(Context context , AttributeSet attrs){
        super(context,attrs);

        Game.getInstance().createField(context);

        setNumColumns(Game.X_RANGE);
        setAdapter(new FieldAdapter());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // The Adapter for the Field:
    private static class FieldAdapter extends BaseAdapter {

        // Returns Game size
        @Override
        public int getCount() {
            Game.getInstance();
            return Game.X_RANGE * Game.Y_RANGE;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // Returns Game Cell:
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            return Game.getInstance().getCellAt(position);
        }
    }
}