package com.example.juegodelconnect4.Logica;

import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewTreeObserver;

import com.example.juegodelconnect4.R;
import com.example.juegodelconnect4.Screens.Resultat;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    private State state = State.RED;
    private final int toWin = 4;
    private boolean hasWinner;

    private GridView gridview, buttongrid;
    private LinearLayout c1, g1;
    private Table table;
    private TableRow tableRow;
    private Board board;

    boolean time;
    int selectedtime;
    int boardSize;

    private Intent in;
    private TextView timertext;
    private ImageView tornImage;

    private Handler mHandler = new Handler();
    private Runnable timertask;

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
        tornImage = (ImageView) findViewById(R.id.ficha);
        c1 = (LinearLayout) findViewById(R.id.cl);
        g1 = (LinearLayout) findViewById(R.id.gl);

        init();
        time();
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }*/

    public void init(){
        this.state = State.RED;

        board = new Board(boardSize);
        gridview.setNumColumns(boardSize);
        buttongrid.setNumColumns(boardSize);
        //table = new Table(this, board/*, this*/);
        //table.notifyDataSetChanged();
        //tableRow = new TableRow(this, board, this);
        //tableRow.notifyDataSetChanged();
        //buttongrid.setAdapter(tableRow);
        //gridview.setAdapter(table);
        ViewTreeObserver vto = g1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                  
                    g1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    g1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width  = g1.getMeasuredWidth();
                int height = g1.getMeasuredHeight();
                int boardSize = Math.min(width, height);
                table = new Table(getApplicationContext(), board, boardSize);
                table.notifyDataSetChanged();
                gridview.setAdapter(table);
                tableRow = new TableRow(getApplicationContext(), board, Game.this, boardSize);
                tableRow.notifyDataSetChanged();
                buttongrid.setAdapter(tableRow);
            }
        });

    }

    // state members related with time
    public void time(){
        timertask = new Runnable() {
            @Override
            public void run() {
                timertext.setText(String.valueOf(selectedtime) + getResources().getString(R.string.tformat));
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
    //public Game(int size, int toWin) {  }
    //  getters and setters
  
    Position playOpponent() {
        // Controla on farà el següent moviment la CPU, primer serà aleatori i després s'implementara una heurística.
        return new Position(0,0);
    }

    void toggleTurn() {
        if(state == State.RED){
            this.state = State.YELLOW;
            tornImage.setImageResource(R.drawable.y);
        }else{
            this.state = State.RED;
            tornImage.setImageResource(R.drawable.r);
        }
    }
    void manageTime() {
        // Es el primer que es comprovara quan es faci un moviment, si es controla el temps i s'ha excedit, s'acaba la partida.
        if(time){

        }
    }
    boolean checkForFinish() {
        // comprovar hasValidMoves  i max connected al fer un moviment.
        return false;
    }

    void drop(int col){
        if(board.hasValidMoves()) {
            Position occupyPos = board.occupyCell(col, state);
            if (occupyPos != null) {
                toggleTurn();
                table.notifyDataSetChanged();
                System.out.println("maxconnectec " + board.maxConnected(occupyPos));
                if(board.maxConnected(occupyPos) == toWin/*si checkForFInish comencem tot el procés per anar a Resultat*/){
                    // acabament()
                    startActivity(new Intent(this, Resultat.class));
                    mHandler.removeCallbacks(timertask);
                    finish();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.fullcol), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
