package com.example.juegodelconnect4.Database;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.juegodelconnect4.Fragments.DetailRegActivity;
import com.example.juegodelconnect4.Fragments.QueryFrag;
import com.example.juegodelconnect4.Fragments.RegFrag;
import com.example.juegodelconnect4.R;


public class AccessBDActivity extends AppCompatActivity implements QueryFrag.ConsultaListener{
    private boolean tablet;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        //QueryFrag qf = (QueryFrag) getSupportFragmentManager().findFragmentById(R.id.queryfrag);
        //qf.setConsultaListener(this);

        tablet = getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if(tablet){
            data = savedInstanceState.getStringArray(getResources().getString(R.string.data));
            RegFrag reg = (RegFrag) getSupportFragmentManager().findFragmentById(R.id.regfrag);
            reg.showInfo(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(tablet) outState.putStringArray(getResources().getString(R.string.data), data);
    }

    @Override
    public void itemSelected(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) l.getAdapter().getItem(position);

        data = new String[]{
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
        };

        if(tablet){
            RegFrag reg = (RegFrag) getSupportFragmentManager().findFragmentById(R.id.regfrag);
            reg.showInfo(data);
        }else{
            Intent in = new Intent(getApplicationContext(), DetailRegActivity.class);
            in.putExtra(getResources().getString(R.string.detall), data);
            startActivity(in);
        }
    }

    public void torna(View view){finish();}
}