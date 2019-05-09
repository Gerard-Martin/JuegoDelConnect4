package com.example.juegodelconnect4.Logica;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Build;
import android.os.Message;
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
    int expendtime = 0;
    int boardSize;

    private Intent in;
    private Bundle extras;
    private TextView timertext;
    private ImageView tornImage;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                manageTime();
            }
        }
    };
    private Runnable timertask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.state = State.RED;

        in = getIntent();
        extras = in.getBundleExtra(getResources().getString(R.string.extrasbundle));

        time = extras.getBoolean(getResources().getString(R.string.timekey), false);
        boardSize = extras.getInt(getResources().getString(R.string.sizekey), 7);
        selectedtime = extras.getInt(getResources().getString(R.string.timespend), 25);

        gridview = (GridView) findViewById(R.id.gridView);
        buttongrid = (GridView) findViewById(R.id.buttongrid);
        timertext = (TextView) findViewById(R.id.timertext);
        tornImage = (ImageView) findViewById(R.id.ficha);
        c1 = (LinearLayout) findViewById(R.id.c1);
        g1 = (LinearLayout) findViewById(R.id.g1);

        if(time) timertext.setTextColor(Color.RED);
        else timertext.setTextColor(Color.BLUE);

        init();
        time();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        board = savedInstanceState.getParcelable(getResources().getString(R.string.board));
        selectedtime = savedInstanceState.getInt(getResources().getString(R.string.timespend));
        expendtime = savedInstanceState.getInt(getResources().getString(R.string.total));
        time = savedInstanceState.getBoolean(getResources().getString(R.string.timekey));
        extras = savedInstanceState.getBundle(getResources().getString(R.string.extrasbundle));
        state = State.valueOf(savedInstanceState.getString(getResources().getString(R.string.state)));

        timertext.setText(String.valueOf((selectedtime >= 0) ? selectedtime : 0) +
                getResources().getString(R.string.tformat));
        if(state == State.RED){
            tornImage.setImageResource(R.drawable.r);
        }else{
            tornImage.setImageResource(R.drawable.y);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHandler.removeCallbacks(timertask);
        outState.putParcelable(getResources().getString(R.string.board), board);
        outState.putInt(getResources().getString(R.string.timespend), selectedtime);
        outState.putInt(getResources().getString(R.string.total), expendtime);
        outState.putBoolean(getResources().getString(R.string.timekey), time);
        outState.putBundle(getResources().getString(R.string.extrasbundle), extras);
        outState.putString(getResources().getString(R.string.state), state.toString());
    }

    public void init(){
        board = new Board(boardSize);
        gridview.setNumColumns(boardSize);
        buttongrid.setNumColumns(boardSize);

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
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    gridview.setHorizontalSpacing(boardSize- Math.max(width, height));
                }
                gridview.setAdapter(table);
                tableRow = new TableRow(getApplicationContext(), board, Game.this, boardSize);
                tableRow.notifyDataSetChanged();
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    buttongrid.setHorizontalSpacing(boardSize- Math.max(width, height));
                }
                buttongrid.setAdapter(tableRow);
            }
        });

    }

    // state members related with time
    @SuppressLint("SetTextI18n")
    public void time(){
        timertask = new Runnable() {
            @Override
            public void run() {
                selectedtime -= 1;
                expendtime += 1;
                if(selectedtime == 0 && time) mHandler.sendEmptyMessage(0);
                else mHandler.postDelayed(this, 1000);
                //if(!time)timertext.setText(String.valueOf(expendtime) + getResources().getString(R.string.tformat));
                //else
                if(selectedtime >= 0) timertext.setText(String.valueOf(selectedtime) +
                        getResources().getString(R.string.tformat));
            }
        };
        try{
            //if(!time)timertext.setText(String.valueOf(expendtime) + getResources().getString(R.string.tformat));
            //else
            if(selectedtime >= 0) timertext.setText(String.valueOf(selectedtime) +
                    getResources().getString(R.string.tformat));
            mHandler.removeCallbacks(timertask);
            mHandler.postDelayed(timertask, 1000);
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
        //toggleTurn(); // Desactivat de moment
        return null;
    }

    void toggleTurn() {
        if(state == State.RED){
            this.state = State.YELLOW;
            tornImage.setImageResource(R.drawable.y);
            playOpponent();
        }else{
            this.state = State.RED;
            tornImage.setImageResource(R.drawable.r);
        }
    }
    void manageTime() {
        // Es el primer que es comprovara quan es faci un moviment, si es controla el temps i s'ha excedit, s'acaba la partida.
        if(time){
            // Gestionar-ho d'alguna forma amb el handler
            extras.putString(getResources().getString(R.string.fin),
                    getResources().getString(R.string.timespend));
            acabament();
        }
    }
    /*boolean checkForFinish(Position pos) {
        // comprovar hasValidMoves  i max connected al fer un moviment.
        return !board.hasValidMoves() || board.maxConnected(pos) == this.toWin;
    }*/

    void drop(int col){
        Position occupyPos = board.occupyCell(col, state);
        if (occupyPos != null) {
            table.notifyDataSetChanged();
            //System.out.println("maxconnectec " + board.maxConnected(occupyPos));
            //if(checkForFinish(occupyPos))//si checkForFinish comencem tot el procés per anar a Resultat
            checkFinalPartida(occupyPos);
            toggleTurn();
        } else {
            Toast.makeText(this, getResources().getString(R.string.fullcol), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkFinalPartida(Position pos){
        if(board.maxConnected(pos) == toWin){
            if(state == State.RED) extras.putString(getResources().getString(R.string.fin),
                    getResources().getString(R.string.guanyat));
            else extras.putString(getResources().getString(R.string.fin),
                    getResources().getString(R.string.perdut));
            acabament();
        }else if(!board.hasValidMoves()) {
            extras.putString(getResources().getString(R.string.fin),
                    getResources().getString(R.string.emt));
            acabament();
        }
    }

    private void acabament(){
        mHandler.removeCallbacks(timertask);
        extras.putInt(getResources().getString(R.string.total), expendtime);
        Intent resultat = new Intent(this, Resultat.class);
        resultat.putExtra(getResources().getString(R.string.extrasbundle), extras);
        startActivity(resultat);
        finish();
    }

   /* public void imageToast(String s, int d){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(d);
        TextView text = layout.findViewById(R.id.text);
        text.setText(s);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void notifier(){
        if(equals(getResources().getString(R.string.cpuguanyat))){
            imageToast(getResources().getString(R.string.cpuguanyat), R.drawable.lost);
        }else if (equals(getResources().getString(R.string.jugadorguanyat))){
            imageToast(getResources().getString(R.string.jugadorguanyat), R.drawable.win);
        }else if (equals(getResources().getString(R.string.emt))){
            imageToast(getResources().getString(R.string.emt), R.drawable.tie);
        }else{
            imageToast(getResources().getString(R.string.tt), R.drawable.timer);
        }
    }*/

}
