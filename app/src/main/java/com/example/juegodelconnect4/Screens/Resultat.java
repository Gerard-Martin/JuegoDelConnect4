package com.example.juegodelconnect4.Screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juegodelconnect4.R;

import java.util.Calendar;

public class Resultat extends AppCompatActivity {

    private int total;
    private String alias;
    private int size;
    private String date;
    private String fin;
    private String logc;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        EditText ti = findViewById(R.id.da);

        Intent in = getIntent();
        extras = in.getBundleExtra(getResources().getString(R.string.extrasbundle));

        fin = extras.getString(getResources().getString(R.string.fin));
        alias = extras.getString(getResources().getString(R.string.alias));
        total = extras.getInt(getResources().getString(R.string.total), 0);
        size = extras.getInt(getResources().getString(R.string.sizekey), 0);

        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        ti.setText(date);

        customButtons();
        if(fin == null){fin = new String();}
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
        System.exit(0);
    }

    public void compose() {
        EditText log;
        log = findViewById(R.id.vall);
        if(fin.equals(getResources().getString(R.string.emt))){
            logc = String.format(getResources().getString(R.string.base), alias, size, total)+'\n';
            logc += getResources().getString(R.string.emt);
            imageToast(getResources().getString(R.string.emt), R.drawable.tie);
        }else{
            logc = String.format(getResources().getString(R.string.base), alias, size, total)+'\n';
            if(fin.equals(getResources().getString(R.string.guanyat))){
                logc += getResources().getString(R.string.guanyat);
                imageToast(getResources().getString(R.string.guanyat), R.drawable.win);
            }else if(fin.equals(getResources().getString(R.string.perdut))){
                logc += getResources().getString(R.string.perdut);
                imageToast(getResources().getString(R.string.perdut), R.drawable.lost);
            }else{
                logc += getResources().getString(R.string.timespend);
                imageToast(getResources().getString(R.string.timespend), R.drawable.timer);
            }
        }
        log.setText(logc);
    }

    public void imageToast(String s, int d){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(d);
        TextView text = layout.findViewById(R.id.text);
        text.setText(s);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
