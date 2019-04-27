package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juegodelconnect4.R;

public class Configuracio extends AppCompatActivity {
    public int size = 7;
    public String alias;
    public boolean timer;
    int selected = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio);
    }

    public void comencar(View view) {
        startActivity(new Intent(this, Resultat.class));
        finish();
    }

    public void seekBar(){
        SeekBar s = findViewById(R.id.sb);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView t = findViewById(R.id.mida);
                int MIN = 5;
                size = MIN + progress;
                String message = " Mida graella eleccionada: " + size;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                t.setText(message);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }

    public void picker(){
        NumberPicker numberPicker = findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(150);
        numberPicker.setMinValue(0);
        numberPicker.setValue(25);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                selected = newVal;
            }
        });
    }
}

