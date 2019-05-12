package com.example.juegodelconnect4.Logica;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.juegodelconnect4.R;


public class Table extends BaseAdapter {
    private Context context;
    private Board board;

    private int size, boardSize;

    Table(Context context, Board board, int boardsize) {
        this.context = context;
        this.board = board;
        this.boardSize = boardsize;
        this.size = board.getSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return size*size;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView token;
        if (convertView == null) {
            token = new ImageView(context);
            token.setLayoutParams(new GridView.LayoutParams(boardSize / size, boardSize / size));
            token.setScaleType(ImageView.ScaleType.FIT_CENTER);
            token.setBackgroundColor(context.getColor(R.color.colorBoard));
            token.setPadding(15, 15, 15, 15);

        } else {
            token = (ImageView) convertView;
        }
        final int row = position / this.size;
        final int column = position % this.size;
        final Position pos= new Position(row, column);

        if (board.isYellowCell(pos)){
            token.setImageResource(R.drawable.y );
        } else if (this.board.isRedCell(pos)){
            token.setImageResource(R.drawable.r);
        }else{
            token.setImageResource(R.drawable.w);
        }
        return token;
    }
}