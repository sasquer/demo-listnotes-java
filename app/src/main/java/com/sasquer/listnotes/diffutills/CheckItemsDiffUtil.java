package com.sasquer.listnotes.diffutills;

import androidx.recyclerview.widget.DiffUtil;

import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;

import java.util.List;

public class CheckItemsDiffUtil extends DiffUtil.Callback {

    private List<NoteCheckItem> mOldList, mNewList;

    public CheckItemsDiffUtil(List<NoteCheckItem> oldList, List<NoteCheckItem> newList) {
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
        return mOldList.get(oldItemPosition).hashCode() == (mNewList.get(newItemPosition).hashCode());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean equalsValue = mOldList.get(oldItemPosition).getValue().equals(mNewList.get(newItemPosition).getValue());
        boolean equalsChecked = mOldList.get(oldItemPosition).isChecked() == equals(mNewList.get(newItemPosition).isChecked());
        return equalsValue && equalsChecked;
    }
}
