package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juegodelconnect4.R;

public class Configuracio extends AppCompatActivity {
    public int size = 7;
    //public String alias;
    //public boolean timer;
    int selected = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio);
        seekBar();
        checkBox();
        picker();
    }


    public void comencar(View view) {
        EditText aliasc = findViewById(R.id.aliasc);
        SeekBar grid = findViewById(R.id.sb);
        CheckBox time = findViewById(R.id.checkBox);
        if(TextUtils.isEmpty(aliasc.getText().toString())){
            aliasc.setError(getResources().getString(R.string.nomerror));
        }else {
            Intent in = new Intent(this, Resultat.class);
            in.putExtra(getResources().getString(R.string.alias), aliasc.getText().toString());
            in.putExtra(getResources().getString(R.string.sizekey), size);
            in.putExtra(getResources().getString(R.string.timerkey), time.isChecked());
            in.putExtra(getResources().getString(R.string.timespend), selected);
            startActivity(in);
            finish();
        }
    }

    public void seekBar(){
        SeekBar s = findViewById(R.id.sb);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView t = findViewById(R.id.mida);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int MIN = 5;
                size = MIN + progress;
                String message = " Mida graella eleccionada: " + size;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                t.setText(message);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ;
            }

        });
    }

    public void checkBox(){
        final CheckBox checktime = findViewById(R.id.checkBox);
        checktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView timetext = findViewById(R.id.top);
                NumberPicker timeselected = findViewById(R.id.numberpicker);
                if(checktime.isChecked()){
                    timetext.setVisibility(View.VISIBLE);
                    timeselected.setVisibility(View.VISIBLE);
                }else{
                    timetext.setVisibility(View.INVISIBLE);
                    timeselected.setVisibility(View.INVISIBLE);
                }
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

