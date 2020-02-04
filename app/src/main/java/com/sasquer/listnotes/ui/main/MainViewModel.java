package com.sasquer.listnotes.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.sasquer.listnotes.base.BaseViewModel;
import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.utils.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {
    private final SingleLiveEvent<Boolean> isAddClicked = new SingleLiveEvent<>();
    private final MutableLiveData<String> searchField = new MutableLiveData<>();
    private final MutableLiveData<String> messageText = new MutableLiveData<>();
    private final MutableLiveData<String> dialogNameMessage = new MutableLiveData<>();
    private final MutableLiveData<String> dialogPasswordMessage = new MutableLiveData<>();
    private final SingleLiveEvent<Boolean> isNeedShowMessage = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> onSearchItemClicked = new MutableLiveData<>();
    private AppRepository mRepository;
    private LiveData<List<Note>> allNotes;

    @Inject
    public MainViewModel(AppRepository mAppRepository) {
        mRepository = mAppRepository;

        init();
    }

    void init() {
        setSearchField("");
        allNotes = Transformations.switchMap(getSearchField(), input -> {
            if (input.length() > 0)
                return mRepository.getFilteredNotes(input);
            else
                return mRepository.getAllNotes();
        });
    }

    public MutableLiveData<Boolean> getOnSearchItemClicked() {
        return onSearchItemClicked;
    }

    public void setOnSearchItemClicked(boolean value) {
        onSearchItemClicked.setValue(value);
    }

    public SingleLiveEvent<Boolean> isAddClicked() {
        return isAddClicked;
    }

    public void setAddClicked(boolean value) {
        isAddClicked.setValue(value);
    }

    public MutableLiveData<String> getSearchField() {
        return searchField;
    }

    public void setSearchField(String value) {
        searchField.setValue(value);
    }

    public MutableLiveData<String> getMessageText() {
        return messageText;
    }

    public void setMessageText(String value) {
        messageText.setValue(value);
    }

    public SingleLiveEvent<Boolean> isNeedShowMessage() {
        return isNeedShowMessage;
    }

    public void setNeedShowMessage(boolean value) {
        isNeedShowMessage.setValue(value);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}