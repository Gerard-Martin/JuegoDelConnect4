package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.example.juegodelconnect4.Logica.Game;
import com.example.juegodelconnect4.R;

public class Configuracio extends AppCompatActivity {
    private int size = 7;
    //public String alias;
    //public boolean timer;
    private int selected = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio);

        Button start = (Button) findViewById(R.id.comencar);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comencar(v);
            }
        });
        seekBar();
        checkBox();
        picker();
        checkSwitch();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        final CheckBox checktime = findViewById(R.id.checkBox);
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

    public void comencar(View view) {
        EditText aliasc = findViewById(R.id.aliasc);
        Switch cpu = findViewById(R.id.cpu);
        CheckBox time = findViewById(R.id.checkBox);
        //Switch switchbtn = findViewById(R.id.checkBox1);
        if(TextUtils.isEmpty(aliasc.getText().toString())){
            aliasc.setError(getResources().getString(R.string.nomerror));
        }else {
            Bundle extras = new Bundle();
            Intent in = new Intent(this, Game.class);
            extras.putString(getResources().getString(R.string.alias), aliasc.getText().toString());
            extras.putInt(getResources().getString(R.string.sizekey), size);
            extras.putBoolean(getResources().getString(R.string.timekey), time.isChecked());
            extras.putInt(getResources().getString(R.string.timespend), selected);
            extras.putBoolean(getResources().getString(R.string.cpu), cpu.isChecked());
            in.putExtra(getResources().getString(R.string.extrasbundle), extras);
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
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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

    public void checkSwitch(){
       Switch switchbtn = (Switch)findViewById(R.id.cpu);
       switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   String message = " Jugar contra la CPU: Activat ";
                   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

               }else{
                   String message = " Jugar contra la CPU: Desactivat ";
                   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

