package com.sasquer.listnotes.data.local.db;

import androidx.lifecycle.LiveData;

import com.sasquer.listnotes.data.local.db.dao.NoteDao;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.utils.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataBaseHelper implements IDatabaseHelper {

    private final AppDatabase mAppDatabase;
    private final AppExecutors mAppExecutors;
    private NoteDao noteDao;

    @Inject
    public DataBaseHelper(AppDatabase appDatabase, AppExecutors appExecutors) {
        mAppDatabase = appDatabase;
        mAppExecutors = appExecutors;
        noteDao = mAppDatabase.noteDao();
    }

    @Override
    public void insertNote(Note note) {
        Runnable saveRunnable = () -> noteDao.insertNote(note);
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteNote(Note note) {
        Runnable removeRunnable = () -> noteDao.deleteNote(note);
        mAppExecutors.diskIO().execute(removeRunnable);
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    @Override
    public Single<Note> getNoteById(int id) {
        return noteDao.getNoteById(id);
    }

    @Override
    public LiveData<List<Note>> getFilteredNotes(String search) {
        return noteDao.getFilteredNotes(search);
    }
}
