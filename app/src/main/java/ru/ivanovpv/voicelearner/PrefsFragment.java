package ru.ivanovpv.voicelearner;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

/**
 * Created by Pavel on 21.06.2015.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
