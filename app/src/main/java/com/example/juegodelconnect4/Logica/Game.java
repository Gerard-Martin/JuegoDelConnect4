package com.example.juegodelconnect4.Logica;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Build;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewTreeObserver;
import com.example.juegodelconnect4.R;
import com.example.juegodelconnect4.Screens.Resultat;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class Game extends AppCompatActivity {
    protected State state = State.RED;
    private final int toWin = 4;
    private int index = 0;
    private List<Board> saved = new ArrayList<>();

    private GridView gridview, buttongrid;
    private LinearLayout c1, g1;
    private Table table;
    private TableRow tableRow;
    private Board board;

    boolean time, cpu;
    private int selectedtime;
    private int expendtime = 0;
    private Random rand = new Random();
    private int boardDimen, boardSize;
    private int width, height;

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
    private Runnable timertask = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            selectedtime -= 1;
            expendtime += 1;
            if(selectedtime == 0 && time) mHandler.sendEmptyMessage(0);
            else mHandler.postDelayed(this, 1000);
            if(selectedtime >= 0) timertext.setText(String.valueOf(selectedtime) +
                    getResources().getString(R.string.tformat));
        }
    };
    private Runnable oponentTask = new Runnable() {
        @Override
        public void run() {
            int column = rand.nextInt(boardSize);
            Position oponentPos = board.occupyCell(column, state);
            while(oponentPos == null && board.hasValidMoves()){
                column = rand.nextInt(boardSize);
                oponentPos = board.occupyCell(column, state);
            }
            table.notifyDataSetChanged();
            checkFinalPartida(oponentPos);
            toggleTurn();
            return;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.state = State.RED;

        in = getIntent();
        extras = in.getBundleExtra(getResources().getString(R.string.extrasbundle));

        time = extras.getBoolean(getResources().getString(R.string.timekey), false);
        cpu = extras.getBoolean(getResources().getString(R.string.cpu), false);
        boardSize = extras.getInt(getResources().getString(R.string.sizekey), 7);
        selectedtime = extras.getInt(getResources().getString(R.string.timespend), 25);

        gridview = (GridView) findViewById(R.id.gridView);
        buttongrid = (GridView) findViewById(R.id.buttongrid);
        timertext = (TextView) findViewById(R.id.timertext);
        tornImage = (ImageView) findViewById(R.id.ficha);
        this.c1 = (LinearLayout) findViewById(R.id.c1);
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
        cpu = savedInstanceState.getBoolean(getResources().getString(R.string.cpu));
        extras = savedInstanceState.getBundle(getResources().getString(R.string.extrasbundle));
        state = State.valueOf(savedInstanceState.getString(getResources().getString(R.string.state)));
        saved = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.list));
        index = savedInstanceState.getInt(getResources().getString(R.string.indexlist));

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
        outState.putBoolean(getResources().getString(R.string.cpu), cpu);
        outState.putBundle(getResources().getString(R.string.extrasbundle), extras);
        outState.putString(getResources().getString(R.string.state), state.toString());
        outState.putParcelableArrayList(getResources().getString(R.string.list), (ArrayList<? extends Parcelable>) saved);
        outState.putInt(getResources().getString(R.string.indexlist), index);
    }

    public void init(){
        board = new Board(boardSize);
        gridview.setNumColumns(boardSize);
        buttongrid.setNumColumns(boardSize);
        if(saved.isEmpty()){
            saved.add(new Board(this.board));
        }
        ViewTreeObserver vto = g1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    g1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    g1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                width  = g1.getMeasuredWidth();
                height = g1.getMeasuredHeight();
                boardDimen = Math.min(width, height);

                table = new Table(getApplicationContext(), board, boardDimen);
                table.notifyDataSetChanged();
                if (boardDimen != width){
                    gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                    buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
                }
                gridview.setAdapter(table);
                tableRow = new TableRow(getApplicationContext(), Game.this, boardDimen);
                tableRow.notifyDataSetChanged();
                if (boardDimen != width){
                    gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                    buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
                }
                buttongrid.setAdapter(tableRow);
            }
        });

    }

    // state members related with time
    @SuppressLint("SetTextI18n")
    public void time(){
        try{
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
    int getBoardSize(){
        return board.getSize();
    }
    /*int heuristic(){
        return 0;
    }*/

    void playOpponent() {
        // Controla on farà el següent moviment la CPU, en una primera versío serà aleatori.
        // S'estableix l'operació mitjançant el handler per tal de fer la cpu concurrent i millorar la seva gestió
        mHandler.postDelayed(oponentTask, 1000);
    }

    void toggleTurn() {
        // Gestiona el canvi de torn.
        if(state == State.RED){
            this.state = State.YELLOW;
            tornImage.setImageResource(R.drawable.y);
            if(cpu && board.hasValidMoves()) playOpponent();
        }else{
            this.state = State.RED;
            tornImage.setImageResource(R.drawable.r);
        }
        tableRow.notifyDataSetChanged();
    }

    void manageTime() {
        // Es el primer que es comprovara quan es faci un moviment, si es controla el temps i s'ha excedit, s'acaba la partida.
        if(time){
            extras.putString(getResources().getString(R.string.fin),
                    getResources().getString(R.string.timespend));
            acabament();
        }
    }

    void drop(int col){
        Position occupyPos = board.occupyCell(col, state);
        if (occupyPos != null) {
            table.notifyDataSetChanged();
            checkFinalPartida(occupyPos);
            toggleTurn();
            addBoard(board);
        } else {
            Toast.makeText(this, getResources().getString(R.string.fullcol), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkFinalPartida(Position pos){
        // Comprova totes les possibilitats de que la partida hagi finalitzat, un cop s'ha realitzat un moviment.
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
        mHandler.removeCallbacks(oponentTask);
        mHandler.removeCallbacks(timertask);
        extras.putInt(getResources().getString(R.string.total), expendtime);
        Intent resultat = new Intent(this, Resultat.class);
        resultat.putExtra(getResources().getString(R.string.extrasbundle), extras);
        startActivity(resultat);
        finish();
    }

    public void unDo(View clickedButton) {
        if(index > 0) {
            index--;
            this.board = new Board(saved.get(index));
            table = new Table(getApplicationContext(), this.board, boardDimen);
            table.notifyDataSetChanged();
            if (boardDimen != width){
                gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
            }
            gridview.setAdapter(table);
            gridview.setNumColumns(boardSize);
            if(!cpu){
                toggleTurn();
            }
        } else {
            Toast.makeText(this, "No hi ha partides per recuperar", Toast.LENGTH_LONG).show();
        }
    }


    public void reDo(View clickedButton) {
        if(index+1 < saved.size()) {
            index++;
            this.board = new Board(saved.get(index));
            table = new Table(getApplicationContext(), this.board, boardDimen);
            table.notifyDataSetChanged();
            if (boardDimen != width){
                gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
            }
            gridview.setAdapter(table);
            gridview.setNumColumns(boardSize);
            if(!cpu){
                toggleTurn();
            }
        } else {
            Toast.makeText(this, "No es pot refer cap acció", Toast.LENGTH_LONG).show();
        }
    }

    public void addBoard(Board b){
        if(index<saved.size()-1) clean();
        saved.add(b);
        index++;
    }

    public void clean(){
        List<Board> temp = new ArrayList<>();
        for(int i=0; i<=index;i++){
            temp.add(new Board(saved.get(i)));
        }
        saved = temp;
    }
}
