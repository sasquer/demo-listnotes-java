package com.sasquer.listnotes.interactors;

import com.sasquer.listnotes.utils.SingleLiveEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToolbarActionsInteractor {
    private final SingleLiveEvent<Boolean> saveItemClicked = new SingleLiveEvent<>();

    @Inject
    public ToolbarActionsInteractor() {
    }

    public SingleLiveEvent<Boolean> isSaveItemClicked() {
        return saveItemClicked;
    }

    public void setSaveItemClicked(boolean value) {
        saveItemClicked.setValue(value);
    }
}
