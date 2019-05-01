package com.example.juegodelconnect4.Logica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.juegodelconnect4.R;

public class Game extends AppCompatActivity {
    /*private final Board board;
    private final int toWin;
    private Status status;
    private boolean hasWinner;
    private Player turn;*/
    public GridView gridview;
    public Table table;
    public Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    public void init(){
        board = new Board(7);
        gridview = findViewById(R.id.gridView);
        gridview.setNumColumns(board.size);
        table = new Table(this, (board.cells), this);
        table.notifyDataSetChanged();
        gridview.setAdapter(table);
    }

   /* // state members related with time
    public Game(int size, int toWin) { . . . }
    //  getters and setters
    Position playOpponent () { . . . }
    void toggleTurn() { . . . }
    void manageTime() { . . . }
    boolean checkForFinish () {}
    ??? drop(int col)*/
}
