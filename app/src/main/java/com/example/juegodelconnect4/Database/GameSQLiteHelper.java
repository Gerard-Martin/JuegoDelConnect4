package com.example.juegodelconnect4.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GameSQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "game";

    // Table name
    private static final String TABLE_SCORE = "score";

    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_ALIAS = "alias";
    private static final String KEY_DATE = "date";
    private static final String KEY_SIZE = "size";
    private static final String KEY_TIMER = "timer";
    private static final String KEY_TIME = "time";
    private static final String KEY_RESULT = "result";

    public GameSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                                    + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                    + KEY_ALIAS + " TEXT,"
                                    + KEY_DATE + " TEXT,"
                                    + KEY_SIZE + " INTEGER,"
                                    + KEY_TIMER + " INTEGER,"
                                    + KEY_TIME + " TEXT,"
                                    + KEY_RESULT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void addScore(String alias, String date, int size, int timer, String time, String result){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALIAS, alias);
        values.put(KEY_DATE, date);
        values.put(KEY_SIZE, size);
        values.put(KEY_TIMER, timer);
        if(timer == 1) values.put(KEY_TIME, time);
        values.put(KEY_RESULT, result);

        db.insert(TABLE_SCORE, null, values);
        db.close();
    }
}