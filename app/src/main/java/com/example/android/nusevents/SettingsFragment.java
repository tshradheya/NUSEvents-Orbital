package com.example.android.nusevents;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Admin on 22-Jun-17.
 */


public  class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences_settings);
    }

}