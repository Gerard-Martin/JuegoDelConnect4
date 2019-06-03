package com.example.juegodelconnect4.Logica;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Build;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewTreeObserver;

import com.example.juegodelconnect4.Fragments.LogFrag;
import com.example.juegodelconnect4.R;
import com.example.juegodelconnect4.Screens.Resultat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    private boolean fragment = false;
    private TextView tv;

    boolean time, cpu;
    private int selectedtime;
    private int expendtime = 0;
    private Random rand = new Random();
    private int boardDimen, boardSize;
    private int width, height;

    private Bundle extras = new Bundle();
    private TextView timertext;
    private ImageView tornImage;
    private String alias;
    private String log = "";
    private String i, f;

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
            addBoard(new Board(board));
            toggleTurn();
            i=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(new Date().getTime()));
            return;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.state = State.RED;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boardSize = Integer.parseInt(prefs.getString(getResources().getString(R.string.midakey), "7"));
        cpu = prefs.getBoolean(getResources().getString(R.string.modekey), false);
        time = prefs.getBoolean(getResources().getString(R.string.timekey), false);
        alias = prefs.getString(getResources().getString(R.string.aliaskey),"");
        selectedtime =  Integer.parseInt(prefs.getString(getResources().getString(R.string.tempskey), "25"));

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
        log =savedInstanceState.getString(getResources().getString(R.string.log));
        if(log != "" && fragment) tv.setText(log);
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
        outState.putString(getResources().getString(R.string.log), log);
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
        LogFrag lg = (LogFrag) getSupportFragmentManager().findFragmentById(R.id.logfrag);
        if(lg.isInLayout()){
            fragment = true;
            tv = findViewById(R.id.logreg);
            log += buildInit();
            log += '\n';
            tv.setText(log);
        }
        i = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(new Date().getTime()));
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
        if(!cpu){
            i=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(new Date().getTime()));
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
            if(!cpu) addBoard(new Board(board));
            f = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(new Date().getTime()));
            if(fragment){
                buildLog(occupyPos, i, f);
                tv.setText(log);
            }
            toggleTurn();
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
            gridview.setAdapter(table);
            gridview.setNumColumns(boardSize);
            table.notifyDataSetChanged();
            if (boardDimen != width){
                gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
            }
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
            gridview.setAdapter(table);
            gridview.setNumColumns(boardSize);
            table.notifyDataSetChanged();
            if (boardDimen != width){
                gridview.setHorizontalSpacing(boardDimen- Math.max(width, height));
                buttongrid.setHorizontalSpacing(boardDimen- Math.max(width, height));
            }
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

    public String buildInit(){
        if(time) return String.format(getString(R.string.initlog), alias, boardSize, "Amb control de temps");
        else return String.format(getString(R.string.initlog), alias, boardSize, "Sense control de temps");
    }

    public void buildLog(Position pos, String in, String fi){
        String blog = "";
        if(!cpu){
            if(state == State.RED) blog += getString(R.string.troig);
            else blog += getString(R.string.tgroc);
        }
        blog += String.format(getString(R.string.blog), in, fi);
        if(time) blog += String.format(getString(R.string.tres), expendtime);
        blog += String.format(getString(R.string.casoc), pos.getRow(), pos.getColumn());
        blog += '\n';
        log += blog;
    }
}
