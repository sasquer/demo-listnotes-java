package com.sasquer.listnotes.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sasquer.listnotes.data.local.db.entity.Note;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NoteDao {

    /**
     * Insert a new Note
     *
     * @param noteItem
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note noteItem);

    /**
     * Delete Note
     *
     * @param noteItem
     */
    @Delete
    void deleteNote(Note noteItem);

    /**
     * Get all Notes
     */
    @Query("SELECT * FROM Notes")
    LiveData<List<Note>> getAllNotes();

    /**
     * Get filtered Notes for search by title
     *
     * @param title
     */
    @Query("SELECT * FROM Notes WHERE title LIKE '%' || :title ||'%'")
    LiveData<List<Note>> getFilteredNotes(String title);

    /**
     * Get Note by id
     *
     * @param id
     */
    @Query("SELECT * FROM Notes WHERE id = :id")
    Single<Note> getNoteById(int id);
}
