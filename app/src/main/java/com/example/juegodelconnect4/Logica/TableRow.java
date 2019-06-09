package com.example.juegodelconnect4.Logica;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.juegodelconnect4.Fragments.LogFrag;
import com.example.juegodelconnect4.R;

public class TableRow extends BaseAdapter {
    private Context context;
    private Game game;
    private int size, boardSize;

    TableRow(Context cont, Game game, int boardsize){
        this.context = cont;
        this.game = game;
        this.size = game.getBoardSize();
        this.boardSize = boardsize;
    }

    @Override
    public int getCount() {
        return size;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageButton btn;

        if (convertView == null) {
            btn = new ImageButton(context);
            btn.setLayoutParams(new GridView.LayoutParams(boardSize / size, boardSize / size));
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            btn.setScaleType(ImageButton.ScaleType.FIT_XY);
            btn.setBackgroundColor(context.getColor(R.color.transparent));
            btn.setPadding(15, 15, 15, 15);
        } else {
            btn = (ImageButton) convertView;
        }

        if(game.state == State.RED) btn.setImageResource(R.drawable.r);
        else btn.setImageResource(R.drawable.y);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(game.cpu && game.state == State.YELLOW) Toast.makeText(v.getContext(),
                        "Torn del oponent", Toast.LENGTH_SHORT).show();
                else game.drop(position);
            }
        });

        return btn;
    }
}