package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.juegodelconnect4.R;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Button start = findViewById(R.id.comencar);
        Button help = findViewById(R.id.ajuda);
        Button exit = findViewById(R.id.sortir);*/
    }

    public void comencar(View view) {
        startActivity(new Intent(this, Configuracio.class));
        finish();
    }

    public void mostrarAjuda(View view) {
        startActivity(new Intent(this, Ajuda.class));
        finish();
    }

    public void sortir(View view) {
        finish();
    }
}
