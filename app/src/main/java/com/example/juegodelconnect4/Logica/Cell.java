package com.example.juegodelconnect4.Logica;


import android.os.Parcel;
import android.os.Parcelable;


public class Cell implements Parcelable {

    private static final char RED = 'R';
    private static final char YELLOW = 'Y';
    private static final char EMPTY = '.';

    private char state;
    //private State player;

    private Cell(char state) {
        this.state = state;
    }

    static Cell empty() {
        return new Cell(EMPTY);
    }

    static Cell red() {
        return new Cell(RED);
    }

    static Cell yellow() {
        return new Cell(YELLOW);
    }


    boolean isEmpty() {
        return this.state==EMPTY;
    }

    boolean isRed() {
        return this.state==RED;
    }

    boolean isYellow() {
        return this.state==YELLOW;
    }

    char getState(){return this.state;}

    void setRed() {
        this.state=RED;
    }

    void setYellow() {
        this.state=YELLOW;
        //this.player = State.YELLOW;
    }

    void setEmpty() {
        this.state=EMPTY;
        //this.player = State.EMPTY;
    }

    /*void setPlayer(State player){
        if(player != null) this.player = player;
    }*/

    public static final Parcelable.Creator<Cell> CREATOR = new Parcelable.Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel source) {
            return new Cell(source);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }

    };

    private Cell(Parcel in) {
        this.state = (char) in.readInt();
    }
    public String toString() {
        return String.valueOf(this.state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
    }
}