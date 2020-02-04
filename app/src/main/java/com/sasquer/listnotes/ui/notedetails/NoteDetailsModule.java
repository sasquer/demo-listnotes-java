package com.sasquer.listnotes.ui.notedetails;


import androidx.lifecycle.ViewModel;

import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.di.scope.FragmentScope;
import com.sasquer.listnotes.di.viewmodel.ViewModelKey;
import com.sasquer.listnotes.interactors.ToolbarActionsInteractor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class NoteDetailsModule {

    @Provides
    @FragmentScope
    static NoteDetailsFragmentArgs provideArgs(NoteDetailsFragment fragment) {
        return NoteDetailsFragmentArgs.fromBundle(fragment.getArguments());
    }

    @Provides
    @FragmentScope
    static NoteDetailsViewModelFactory bindDetailsViewModelFactory(AppRepository appRepos,
                                                                   ToolbarActionsInteractor toolbarInteractor,
                                                                   NoteDetailsFragmentArgs args) {
        return new NoteDetailsViewModelFactory(appRepos, toolbarInteractor, args);
    }

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(NoteDetailsViewModel.class)
    abstract ViewModel bindNoteDetailsViewModel(NoteDetailsViewModel noteDetailsViewModel);
}


//    @Binds
////    @Provides
//    @Named("NoteDetailsViewModelFactory")
//    abstract ViewModelProvider.Factory bindNoteDetailsViewModelFactory(ViewModelFactory factory);
