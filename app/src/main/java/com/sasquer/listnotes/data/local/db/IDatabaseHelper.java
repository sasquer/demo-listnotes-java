package com.sasquer.listnotes.data.local.db;

import androidx.lifecycle.LiveData;

import com.sasquer.listnotes.data.local.db.entity.Note;

import java.util.List;

import io.reactivex.Single;

public interface IDatabaseHelper {
    void insertNote(Note note);

    void deleteNote(Note note);

    LiveData<List<Note>> getAllNotes();

    Single<Note> getNoteById(int id);

    LiveData<List<Note>> getFilteredNotes(String search);
}
