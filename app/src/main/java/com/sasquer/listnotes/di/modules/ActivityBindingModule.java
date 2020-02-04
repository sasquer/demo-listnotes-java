package com.sasquer.listnotes.di.modules;

import com.sasquer.listnotes.di.scope.ActivityScope;
import com.sasquer.listnotes.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {FragmentBindingModule.class})
    abstract MainActivity bindMainActivity();

}
