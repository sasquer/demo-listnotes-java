package com.sasquer.listnotes.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class PreferencesHelper implements IPreferencesHelper {

    private static final String PREF_KEY_ANY = "PREF_KEY_ANY";

    private final SharedPreferences mPrefs;

    @Inject
    PreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void cleanPreferences() {
        mPrefs.edit().clear().apply();
    }

    @Override
    public String getKey() {
        return mPrefs.getString(PREF_KEY_ANY, null);
    }

    @Override
    public void setKey(String key) {
        mPrefs.edit().putString(PREF_KEY_ANY, key).apply();
    }
}
