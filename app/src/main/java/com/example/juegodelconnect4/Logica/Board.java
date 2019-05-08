package com.example.juegodelconnect4.Logica;


public class Board {

    private final int size;
    private Cell[][] cells;

    private int yellow;
    private int red;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.red = 0;
        this.yellow = 0;
        for(int i = 0; i< size;i++) {
            for (int j = 0; j < size; j++) {
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
        for(int i = 0; i < size;i++) {
            for (int j = 0; j < size; j++) {
                if(this.cells[i][j].isRed()) temp[i][j] = Cell.red();
                else if(this.cells[i][j].isYellow()) temp[i][j] = Cell.yellow();
                else temp[i][j] = Cell.empty();
            }
        }
        return temp;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmptyCell(Position position) {
        return cells[position.getRow()][position.getColumn()].isEmpty();
    }

    public boolean isRedCell(Position position) {
        return cells[position.getRow()][position.getColumn()].isRed();
    }

    public boolean isYellowCell(Position position) {
        return cells[position.getRow()][position.getColumn()].isYellow();

    }

    /*Position occupyCell (int column, Player player) {
    }*/
    Position occupyCell (int column, State player) {
        int row = firstEmptyRow(column);
        if (row == -1) return null;
        Position pos = new Position(row, column);
        if(player == State.RED) cells[pos.getRow()][pos.getColumn()].setRed();
        else cells[pos.getRow()][pos.getColumn()].setYellow();
        return pos;
    }

    boolean hasValidMoves() {
        for(int i = 0 ; i < size ; i += 1){
            for(int j = 0; j < size ; j += 1){
                if(cells[j][i].isEmpty()) return true;
            }
        }
        return false;
    }

    int firstEmptyRow(int column) {
        // Assume column is playable ...
        for(int i = size-1 ; i >= 0 ; i -= 1){
            if(cells[i][column].isEmpty()) return i;
        }
        return -1;
    }

    // obtains the maximum number of connected positions in any direction }
    int maxConnected(Position position){
        char state = cells[position.getRow()][position.getColumn()].getState();
        char newState;
        int maxConnected, connected;
        maxConnected = 0;
        for (Direction d: Direction.ALL){
            Position moved = position.move(d);
            Position invertMoved = position.move(d.invert());
            connected = 1;
            if(inBoardLimits(moved.getRow(), moved.getColumn())) {
                do {
                    newState = cells[moved.getRow()][moved.getColumn()].getState();
                    if (newState == state) connected += 1;
                    moved = moved.move(d);
                } while (newState == state && inBoardLimits(moved.getRow(), moved.getColumn()));
            }
            if(inBoardLimits(invertMoved.getRow(), invertMoved.getColumn())) {
                do{
                    newState = cells[invertMoved.getRow()][invertMoved.getColumn()].getState();
                    if (newState == state) connected += 1;
                    invertMoved = invertMoved.move(d.invert());
                }while (newState == state && inBoardLimits(invertMoved.getRow(), invertMoved.getColumn()));
            }
            maxConnected = Math.max(maxConnected, connected);
        }
        return maxConnected;
    }

    private boolean inBoardLimits(int row, int col){
        return row < size && row >= 0 && col < size && col >= 0;
    }
}