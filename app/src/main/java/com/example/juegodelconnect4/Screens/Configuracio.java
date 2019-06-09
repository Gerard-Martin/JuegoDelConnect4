package com.example.juegodelconnect4.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.example.juegodelconnect4.R;

public class Configuracio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();

    }

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.configscreen);

            ListPreference midaGraella = (ListPreference) findPreference(getResources().getString(R.string.midakey));
            midaGraella.setTitle(getResources().getString(R.string.midagraella)+ midaGraella.getValue());
            midaGraella.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String val = (String) newValue;
                    preference.setTitle(getResources().getString(R.string.midagraella) + val);
                    return true;
                }
            });

            final CheckBoxPreference timer = (CheckBoxPreference) findPreference(getResources().getString(R.string.timekey));
            timer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if(!timer.isChecked()){
                        EditTextPreference temps = (EditTextPreference) findPreference(getResources().getString(R.string.tempskey));
                        temps.setText("25");
                    }
                    return true;
                }
            });
        }
    }
}