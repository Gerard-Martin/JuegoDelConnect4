package com.example.juegodelconnect4.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.juegodelconnect4.R;


public class DetailRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle onSavedIntance){
        super.onCreate(onSavedIntance);
        setContentView(R.layout.activity_detail);
        RegFrag reg = (RegFrag) getSupportFragmentManager().findFragmentById(R.id.regfrag);
        reg.showInfo(getIntent().getStringArrayExtra(getResources().getString(R.string.detall)));
    }

    public void torna(View view){
        finish();
    }
}
