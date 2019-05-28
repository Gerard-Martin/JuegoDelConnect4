package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.juegodelconnect4.Logica.Game;
import com.example.juegodelconnect4.R;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void comencar(View view) {
        startActivity(new Intent(this, Game.class));
        finish();
    }

    public void mostrarAjuda(View view) {
        startActivity(new Intent(this, Ajuda.class));
        finish();
    }


    public void sortir(View view) {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent in = new Intent(this, Configuracio.class);
                startActivity(in);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
