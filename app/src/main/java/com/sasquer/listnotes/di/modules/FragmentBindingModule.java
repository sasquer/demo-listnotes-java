package com.sasquer.listnotes.di.modules;

import com.sasquer.listnotes.di.scope.FragmentScope;
import com.sasquer.listnotes.ui.main.MainFragment;
import com.sasquer.listnotes.ui.notedetails.NoteDetailsFragment;
import com.sasquer.listnotes.ui.notedetails.NoteDetailsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteDetailsModule.class})
    abstract NoteDetailsFragment bindNoteDetailsFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract MainFragment bindMainFragment();
}