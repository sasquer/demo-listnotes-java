package com.sasquer.listnotes.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.data.local.db.DataBaseHelper;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;
import com.sasquer.listnotes.data.local.prefs.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppRepository implements IDataSourceManager {
    private final PreferencesHelper mPreferencesHelper;
    private final DataBaseHelper mDatabaseHelper;
    private final MutableLiveData<List<NoteCheckItem>> checkItems = new MutableLiveData<>();
    private final List<NoteCheckItem> dataSet = new ArrayList<>();

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

    @Override
    public MutableLiveData<List<NoteCheckItem>> observeCheckItems() {
        return checkItems;
    }

    @Override
    public void addCheckItem(NoteCheckItem item) {
        dataSet.add(item);
//        notifiDataSetChanged();
    }

    @Override
    public void addCheckItems(List<NoteCheckItem> items) {
        dataSet.clear();
        dataSet.addAll(items);
        notifiDataSetChanged();
    }

    @Override
    public void updateCheckItemsAfterSwipe(List<NoteCheckItem> items) {
        dataSet.clear();
        dataSet.addAll(items);
    }

    @Override
    public void removeCheckItem(int position) {
        dataSet.remove(position);
//        notifiDataSetChanged();
    }

    private void notifiDataSetChanged() {
        checkItems.postValue(dataSet);
    }
}
