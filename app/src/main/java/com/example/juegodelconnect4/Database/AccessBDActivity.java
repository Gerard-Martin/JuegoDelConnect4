package com.example.juegodelconnect4.Database;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juegodelconnect4.Fragments.QueryFrag;
import com.example.juegodelconnect4.R;

public class AccessBDActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        QueryFrag qf = (QueryFrag) getSupportFragmentManager().findFragmentById(R.id.queryfrag);

    }

    public void torna(View view){finish();}
}