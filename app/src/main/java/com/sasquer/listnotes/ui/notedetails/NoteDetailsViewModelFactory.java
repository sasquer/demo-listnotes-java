package com.sasquer.listnotes.ui.notedetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.interactors.ToolbarActionsInteractor;

import javax.inject.Inject;


public class NoteDetailsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final AppRepository mAppRepository;
    @NonNull
    private final ToolbarActionsInteractor toolbarInteractor;
    @NonNull
    private final NoteDetailsFragmentArgs provideArgs;

    @Inject
    public NoteDetailsViewModelFactory(@NonNull AppRepository mAppRepository,
                                       @NonNull ToolbarActionsInteractor toolbarInteractor,
                                       @NonNull NoteDetailsFragmentArgs provideArgs) {
        this.mAppRepository = mAppRepository;
        this.toolbarInteractor = toolbarInteractor;
        this.provideArgs = provideArgs;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteDetailsViewModel(mAppRepository, toolbarInteractor, provideArgs);
    }
}