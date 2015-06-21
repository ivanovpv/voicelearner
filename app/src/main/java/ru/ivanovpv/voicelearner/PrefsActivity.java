package ru.ivanovpv.voicelearner;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.preference.PreferenceFragment;

/**
 * Created by Pavel on 21.06.2015.
 */
public class PrefsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
