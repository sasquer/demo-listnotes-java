package com.sasquer.listnotes.data.local.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public final class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "timeCreated")
    private Long mTimeCreated;


    private Note(@NonNull int id, @Nullable String title) {
        mId = id;
        mTitle = title;
    }

    public Note(@Nullable String title) {
        mTitle = title;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String mLogin) {
        this.mTitle = mLogin;
    }

    @Nullable
    public Long getTimeCreated() {
        return mTimeCreated;
    }

    public void setTimeCreated(@Nullable Long timeCreated) {
        mTimeCreated = timeCreated;
    }

    @Override
    public String toString() {
        return "User :" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'';
    }


}
