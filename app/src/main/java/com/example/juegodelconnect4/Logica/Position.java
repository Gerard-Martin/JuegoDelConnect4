package com.example.juegodelconnect4.Logica;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

public class Position{

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row=row;
        this.column=column;

    }
    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public Position move(Direction direction) {
        return new Position(this.row+direction.getChangeInRow(), this.column+direction.getChangeInColumn());
    }

   boolean isEqualTo(Position other) {
       return other != null &&
               this.getRow() == other.getRow() &&
               this.getColumn() == other.getColumn();
   }
    static int pathLength(Position pos1, Position pos2) {
        int x = abs(pos1.getRow() - pos2.getRow());
        int y = abs(pos1.getColumn() - pos2.getColumn());
        // pos1 and pos2 are aligned horizontally, vertically or diagonally???
        if(pos1.getRow() == pos2.getRow()){
            // mateixa fila
            return y + 1;
        }else if(pos1.getColumn() == pos2.getColumn()){
            // mateixa columna
            return x + 1;
        }else if (abs(pos1.getRow() - pos2.getRow()) == abs(pos1.getColumn() - pos2.getColumn())){
            // en diagonal
            return max(x, y) +1;
        }
        return 0;
    }

}
