package com.sasquer.listnotes.data;


import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.data.local.db.IDatabaseHelper;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;
import com.sasquer.listnotes.data.local.prefs.IPreferencesHelper;

import java.util.List;

public interface IDataSourceManager extends IPreferencesHelper, IDatabaseHelper {

    MutableLiveData<List<NoteCheckItem>> observeCheckItems();

    void addCheckItem(NoteCheckItem item);

    void addCheckItems(List<NoteCheckItem> items);

    void updateCheckItemsAfterSwipe(List<NoteCheckItem> items);

    void removeCheckItem(int position);
}
