package com.example.juegodelconnect4.Logica;


public class Board {

    public final int size;
    public Cell[][] cells;

    public int yellow;
    public int red;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.red = 0;
        this.yellow = 0;
        for(int i = 0; i< this.size();i++) {
            for (int j = 0; j < this.size(); j++) {
                this.cells[i][j]=Cell.empty();
            }
        }
    }

    Board(Board b) {
        this.size = b.size;
        this.cells = b.getCells();
        this.red = b.red;
        this.yellow = b.yellow;
    }

    private Cell[][] getCells() {
        Cell[][] temp = new Cell[size][size];
        for(int i = 0; i< this.size();i++) {
            for (int j = 0; j < this.size(); j++) {
                if(this.cells[i][j].isRed()) temp[i][j] = Cell.red();
                else if(this.cells[i][j].isYellow()) temp[i][j] = Cell.yellow();
                else temp[i][j] = Cell.empty();
            }
        }
        return temp;
    }

    private int size() {
        return size;
    }
/*
    Position occupyCell (int column, Player player) {

    }

    boolean hasValidMoves() { //Todo:}
    int firstEmptyRow(int column) {
        // Assume column is playable ...
    }
    int maxConnected(Position position) { //Todo:}
    // obtains the maximum number of connected positions in any direction }*/
}