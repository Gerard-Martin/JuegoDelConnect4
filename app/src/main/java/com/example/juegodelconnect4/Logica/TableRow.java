package com.example.juegodelconnect4.Logica;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.juegodelconnect4.R;

public class TableRow extends BaseAdapter {
    private Context context;
    final Cell[][] board;
    private int size;
    private Game game;
    boolean last = true;

    TableRow(Context context, Cell[][] board, Game game, int size) {
        this.context = context;
        this.board = board;
        this.game = game;
        this.size = size;
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
        return board.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageButton token;
        if (convertView == null
        ) {
            token = new ImageButton(context);
            token.setLayoutParams(new GridView.LayoutParams(size / board.length, size / board.length));
            token.setScaleType(ImageView.ScaleType.FIT_CENTER);
            token.setScaleType(ImageButton.ScaleType.FIT_XY);
            token.setBackgroundColor(context.getColor(R.color.transparent));
            token.setPadding(15, 15, 15, 15);
        } else {
            token = (ImageButton) convertView;
        }


        final int row = position / this.board.length;

        token.setImageResource(R.drawable.r);
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(last) token.setImageResource(R.drawable.y);
                else  token.setImageResource(R.drawable.r);
                last = !last;
            }
        });
        //final Position p = new Position(column,row);
       /* token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.board.isHint(p)) {
                    game.move(p);
                    if(game.cpu) game.heuristic();
                    else if (game.canPlay(game.getOther())) {
                        game.state = game.getOther();
                    }
                    game.setHints();
                    game.table.notifyDataSetChanged();
                    game.addBoard(new Board(game.board));
                } else {
                    Toast.makeText(context, R.string.posnovalida, Toast.LENGTH_LONG).show();
                }
            }
        });*/
        return token;
    }
}