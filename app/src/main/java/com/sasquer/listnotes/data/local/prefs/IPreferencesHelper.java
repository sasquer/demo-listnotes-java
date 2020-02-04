package com.sasquer.listnotes.data.local.prefs;

public interface IPreferencesHelper {

    void cleanPreferences();

    String getKey();

    void setKey(String key);
}
