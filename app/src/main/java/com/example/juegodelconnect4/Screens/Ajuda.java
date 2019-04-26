package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.juegodelconnect4.R;

public class Ajuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
    }
    public void tornarPrincipal(View clickedButton) {
        startActivity(new Intent(this, Main.class));
        finish();
    }
}
