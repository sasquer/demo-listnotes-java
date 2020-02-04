package com.sasquer.listnotes.data;

import androidx.lifecycle.LiveData;

import com.sasquer.listnotes.data.local.db.DataBaseHelper;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.data.local.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppRepository implements IDataSourceManager {
    private final PreferencesHelper mPreferencesHelper;
    private final DataBaseHelper mDatabaseHelper;

    @Inject
    AppRepository(PreferencesHelper preferencesHelper, DataBaseHelper databaseHelper) {
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void insertNote(Note note) {
        mDatabaseHelper.insertNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        mDatabaseHelper.deleteNote(note);
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return mDatabaseHelper.getAllNotes();
    }

    @Override
    public Single<Note> getNoteById(int id) {
        return mDatabaseHelper.getNoteById(id);
    }

    @Override
    public LiveData<List<Note>> getFilteredNotes(String search) {
        return mDatabaseHelper.getFilteredNotes(search);
    }

    @Override
    public void cleanPreferences() {
        mPreferencesHelper.cleanPreferences();
    }

    @Override
    public String getKey() {
        return mPreferencesHelper.getKey();
    }

    @Override
    public void setKey(String key) {
        mPreferencesHelper.setKey(key);
    }
}
