package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.juegodelconnect4.R;

import java.util.Calendar;

public class Resultat extends AppCompatActivity {

    private int total;
    private String alias;
    private int size;
    private String date;
    private String fin;
    private String logc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        EditText ti = findViewById(R.id.da);

        Intent in = getIntent();
        fin = in.getStringExtra(getResources().getString(R.string.fin));
        alias = in.getStringExtra(getResources().getString(R.string.alias));
        total = in.getIntExtra(getResources().getString(R.string.total), 0);
        size = in.getIntExtra(getResources().getString(R.string.sizekey), 0);

        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        ti.setText(date);

        customButtons();
        compose();
    }

    public void enviarPartida(View view) {
        EditText e = findViewById(R.id.em);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + e.getText().toString()));
        intent.putExtra(Intent.EXTRA_SUBJECT, date);
        intent.putExtra(Intent.EXTRA_TEXT, logc);
        startActivity(intent);
    }

    public void novaPartida(View view) {
        startActivity(new Intent(this, Configuracio.class));
        finish();
    }

    public void sortir(View view) {
        finish();
    }

    public void compose() {
        EditText log;
        log = findViewById(R.id.vall);
        if(fin.equals(getResources().getString(R.string.emt))){
            logc += getResources().getString(R.string.emt);
        }else{
            logc = String.format(getResources().getString(R.string.base), alias, size, total)+'\n';
            if(fin.equals(getResources().getString(R.string.guanyat))){
                logc += getResources().getString(R.string.guanyat);
            }else if(fin.equals(getResources().getString(R.string.perdut))){
                logc += getResources().getString(R.string.perdut);
            }else{
                logc += getResources().getString(R.string.timespend);
            }
        }
        log.setText(logc);
    }

    public void customButtons() {
        if (Build.VERSION.SDK_INT < 23) {
            Button en = findViewById(R.id.enviar);
            Button no = findViewById(R.id.nova);
            Button so = findViewById(R.id.sortir);
            en.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            no.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            so.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
}
