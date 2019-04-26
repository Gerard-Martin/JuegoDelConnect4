package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juegodelconnect4.R;

public class Resultat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
    }

    public void enviarPartida(View view) {

    }

    public void novaPartida(View view) {
        startActivity(new Intent(this, Configuracio.class));
        finish();
    }

    public void sortir(View view) {
        finish();
    }
}
