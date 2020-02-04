package com.sasquer.listnotes.data.local.repository;

import androidx.lifecycle.LiveData;

import com.sasquer.listnotes.data.local.db.entity.Note;

import java.util.List;

public interface INoteRepository {

    LiveData<List<Note>> observableNotesList();

    void addNote(Note item);

    void deleteAllNotes();

    void notifyDataSetChanged();
}
