package com.example.juegodelconnect4.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juegodelconnect4.R;


public class RegFrag extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.item_detail, container, false);
        return view;
    }

    public void showInfo(String[] data){
        TextView alias = getView().findViewById(R.id.aliasa);
        TextView date = getView().findViewById(R.id.date);
        TextView size = getView().findViewById(R.id.size);
        TextView timer = getView().findViewById(R.id.timer);
        TextView time = getView().findViewById(R.id.time);
        TextView result = getView().findViewById(R.id.result);
        if(data[0].equals("")) alias.setText(getString(R.string.noal));
        else alias.setText(getString(R.string.al) + data[0]);
        date.setText(getString(R.string.dat) + data[1]);
        size.setText(getString(R.string.mg) + data[2]);
        if(data[3].equals("0")) timer.setText(getString(R.string.notemps));
        else timer.setText(getString(R.string.contemps));
        time.setText(getString(R.string.temtot)+data[4]);
        result.setText(data[5]);
    }
}
