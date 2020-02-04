package com.sasquer.listnotes.diffutills;

import androidx.recyclerview.widget.DiffUtil;

import com.sasquer.listnotes.data.local.db.entity.Note;

import java.util.List;

public class NoteListDiffUtil extends DiffUtil.Callback {

    private List<Note> mOldList, mNewList;

    public NoteListDiffUtil(List<Note> oldList, List<Note> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == (mNewList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean equalsTitle = mOldList.get(oldItemPosition).getTitle().equals(mNewList.get(newItemPosition).getTitle());
        return equalsTitle;
    }
}
