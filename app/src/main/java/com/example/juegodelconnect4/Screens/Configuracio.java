package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juegodelconnect4.R;

public class Configuracio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio);
    }

    public void comencar(View view) {
        startActivity(new Intent(this, Resultat.class));
        finish();
    }
}
