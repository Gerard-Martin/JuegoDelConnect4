package com.example.juegodelconnect4.Logic;

import android.os.Parcelable;

public class Board {

    private final int size;
    public Cell[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
    }

    Position occupyCell (int column, Player player) { . . . }

    boolean hasValidMoves() { . . . }
    int firstEmptyRow(int column) {
        // Assume column is playable ...
    }
    int maxConnected(Position position) { . . . }
    // obtains the maximum number of connected positions in any direction }
}
