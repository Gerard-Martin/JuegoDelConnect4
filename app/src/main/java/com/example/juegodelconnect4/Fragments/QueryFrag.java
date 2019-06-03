package com.example.juegodelconnect4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.juegodelconnect4.Database.GameSQLiteHelper;
import com.example.juegodelconnect4.R;

public class QueryFrag extends ListFragment {
    private SQLiteDatabase db;
    private GameSQLiteHelper SQLHelper;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        registerForContextMenu(getListView());
        init();
    }

    public void init(){
        String selectQuery = getResources().getString(R.string.query);
        SQLHelper = new GameSQLiteHelper(getActivity().getBaseContext());
        db = SQLHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                R.layout.item_list,
                cursor,
                new String[]{"alias", "date", "result"},
                new int[]{R.id.aliasmain, R.id.datemain, R.id.resultmain}, 0);
        this.setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                "itemList: "+position, Toast.LENGTH_SHORT);
        toast.show();
    }
}
