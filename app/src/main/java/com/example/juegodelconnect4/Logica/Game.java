package com.example.juegodelconnect4.Logica;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.content.Intent;
import android.widget.TextView;

import com.example.juegodelconnect4.R;
import com.example.juegodelconnect4.Screens.Resultat;

public class Game extends AppCompatActivity {
    private State state = State.RED;

    private GridView gridview, buttongrid;
    private Table table;
    private TableRow tableRow;
    private Board board;

    boolean time;
    int selectedtime;
    int boardSize;

    private Intent in;
    private TextView timertext;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        in = getIntent();

        time = in.getBooleanExtra(getResources().getString(R.string.timekey), false);
        boardSize = in.getIntExtra(getResources().getString(R.string.sizekey), 7);
        selectedtime = in.getIntExtra(getResources().getString(R.string.timespend), 25);

        gridview = (GridView) findViewById(R.id.gridView);
        buttongrid = (GridView) findViewById(R.id.buttongrid);
        timertext = (TextView) findViewById(R.id.timertext);

        init();
        time();
    }

    public void init(){
        this.state = State.RED;

        board = new Board(boardSize);
        gridview.setNumColumns(boardSize);
        buttongrid.setNumColumns(boardSize);
        table = new Table(this, board/*, this*/);
        table.notifyDataSetChanged();
        tableRow = new TableRow(this, board/*, this*/);
        tableRow.notifyDataSetChanged();
        buttongrid.setAdapter(tableRow);
        gridview.setAdapter(table);
    }

    public void time(){
        Runnable timertask = new Runnable() {
            @Override
            public void run() {
                timertext.setText(String.valueOf(selectedtime) + " secs.");
                timertext.setTextColor(Color.BLUE);
                selectedtime-=1;
                if(selectedtime >= 0)
                mHandler.postDelayed(this, 1000);
                else ;
            }
        };
        try{
            mHandler.removeCallbacks(timertask);
            mHandler.postDelayed(timertask, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*
**************************Game Logic*************************************
*/
   /* // state members related with time
    public Game(int size, int toWin) { . . . }
    //  getters and setters
    Position playOpponent () { . . . }
    void toggleTurn() { . . . }
    void manageTime() { . . . }
    boolean checkForFinish () {}
    ??? drop(int col)*/
}
