package com.example.juegodelconnect4.Screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juegodelconnect4.Database.GameSQLiteHelper;
import com.example.juegodelconnect4.Logica.Game;
import com.example.juegodelconnect4.R;

import java.util.Calendar;

public class Resultat extends AppCompatActivity {

    private int total;
    private String alias;
    private int size;
    private String date;
    private String fin;
    private String logc;
    private boolean timer;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        EditText ti = findViewById(R.id.da);

        Intent in = getIntent();
        extras = in.getBundleExtra(getResources().getString(R.string.extrasbundle));

        fin = extras.getString(getResources().getString(R.string.fin));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        alias = prefs.getString(getResources().getString(R.string.aliaskey),"");
        total = extras.getInt(getResources().getString(R.string.total), 0);
        size = Integer.parseInt(prefs.getString(getResources().getString(R.string.midakey), "7"));
        timer = prefs.getBoolean(getResources().getString(R.string.timekey), false);

        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        ti.setText(date);

        customButtons();
        if(fin == null){fin = new String();}
        compose();
        if(savedInstanceState == null) writeDB();
    }

    @SuppressLint("IntentReset")
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
        startActivity(new Intent(this, Game.class));
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
            imageToast(getResources().getString(R.string.emt), R.drawable.tie);
            logc = String.format(getResources().getString(R.string.base), alias, size, total)+'\n';
            logc += getResources().getString(R.string.emt);
            imageToast(getResources().getString(R.string.emt), R.drawable.tie);
        }else{
            logc = String.format(getResources().getString(R.string.base), alias, size, total)+'\n';
            if(fin.equals(getResources().getString(R.string.guanyat))){
                imageToast(getResources().getString(R.string.guanyat), R.drawable.win);
                logc += getResources().getString(R.string.guanyat);
                imageToast(getResources().getString(R.string.guanyat), R.drawable.win);
            }else if(fin.equals(getResources().getString(R.string.perdut))){
                imageToast(getResources().getString(R.string.perdut), R.drawable.lost);
                logc += getResources().getString(R.string.perdut);
                imageToast(getResources().getString(R.string.perdut), R.drawable.lost);
            }else{
                imageToast(getResources().getString(R.string.timespend), R.drawable.timer);
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

    public void writeDB(){
        GameSQLiteHelper SQLHelper = new GameSQLiteHelper(this.getApplicationContext());

        SQLHelper.addScore(alias, date, size, timer ? 1: 0, Integer.toString(total), fin);
        Toast toast = Toast.makeText(this,getString(R.string.guardatdata), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}
