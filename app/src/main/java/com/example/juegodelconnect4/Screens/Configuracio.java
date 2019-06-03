package com.example.juegodelconnect4.Screens;

import android.app.Activity;
import android.os.Bundle;
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
        }
    }
}