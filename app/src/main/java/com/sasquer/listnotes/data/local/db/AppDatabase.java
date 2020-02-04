package com.sasquer.listnotes.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sasquer.listnotes.data.local.db.dao.NoteDao;
import com.sasquer.listnotes.data.local.db.entity.Note;

@Database(entities = {Note.class}, version = AppDatabase.DB_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    static final int DB_VERSION = 1;

    public abstract NoteDao noteDao();
}
