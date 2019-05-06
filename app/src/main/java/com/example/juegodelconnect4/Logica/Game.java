package com.example.juegodelconnect4.Logica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.juegodelconnect4.R;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
/*    private final Board board;
    private final int toWin;
    private Status status;
    private boolean hasWinner;
    private Player turn;*/
    public boolean time;
    public String alias;
    public int size;
    public int total;
    public GridView gridview, choose;
    public Table table;
    public int timetospend;
    public Timer timer;
    public TextView temps;
    private String fin;
    public TableRow tableRow;
    public Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        timer = null;
    }

    public void init(){
        board = new Board(7);
        gridview = findViewById(R.id.gridView);
        gridview.setNumColumns(board.size);
        table = new Table(this, (board.cells), this);
        table.notifyDataSetChanged();
        gridview.setAdapter(table);
        choose = findViewById(R.id.choose);
        choose.setNumColumns(board.size);
        tableRow = new TableRow(this, (board.cells), this);
        tableRow.notifyDataSetChanged();
        choose.setAdapter(tableRow);
    }

    /*public void acabarPartida() {
        fin = getFinished();
        //notifier();
        timer.cancel();
        Intent in = new Intent(this, Resultat.class);
        in.putExtra(getResources().getString(R.string.timerkey),time);
        in.putExtra(getResources().getString(R.string.fin), fin);
        in.putExtra(getResources().getString(R.string.redkey), board.red);
        in.putExtra(getResources().getString(R.string.yellowkey), board.yellow);
        in.putExtra(getResources().getString(R.string.stablishedkey), timetospend);
        in.putExtra(getResources().getString(R.string.alias), alias);
        in.putExtra(getResources().getString(R.string.sizekey), size);
        in.putExtra(getResources().getString(R.string.total), total);
        startActivity(in);
        finish();
    }*/

    private void timerfunctions(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(time){
                            if(timetospend > total){
                                temps.setText(String.format(getString(R.string.temps), timetospend-total));
                            }else{
                                //acabarPartida();
                            }
                        }else temps.setText(String.format(getString(R.string.tempsinici), total));
                        total++;
                    }
                });
            }
        }, 1000, 1000);
    }

    /*private void notifier(){
        if(fin.equals(getResources().getString(R.string.cpuguanyat))){
            imageToast(getResources().getString(R.string.cpuguanyat), R.drawable.lost);
        }else if (fin.equals(getResources().getString(R.string.jugadorguanyat))){
            imageToast(getResources().getString(R.string.jugadorguanyat), R.drawable.win);
        }else if (fin.equals(getResources().getString(R.string.emt))){
            imageToast(getResources().getString(R.string.emt), R.drawable.tie);
        }else{
            imageToast(getResources().getString(R.string.tt), R.drawable.timer);
        }
    }*/

   /* // state members related with time
    public Game(int size, int toWin) { . . . }
    //  getters and setters
    Position playOpponent () { . . . }
    void toggleTurn() { . . . }
    void manageTime() { . . . }
    boolean checkForFinish () {}
    ??? drop(int col)*/
}
