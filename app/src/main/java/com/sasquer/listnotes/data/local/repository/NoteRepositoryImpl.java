package com.sasquer.listnotes.data.local.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.data.local.db.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements INoteRepository {

    private List<Note> dataSet = new ArrayList<>();
    private MutableLiveData<List<Note>> notesList = new MutableLiveData<>();


    @Override
    public void notifyDataSetChanged() {
        notesList.postValue(dataSet);
    }

    @Override
    public LiveData<List<Note>> observableNotesList() {
        return notesList;
    }

    @Override
    public void addNote(Note item) {
        dataSet.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void deleteAllNotes() {
        dataSet.clear();
        notifyDataSetChanged();
    }
}
