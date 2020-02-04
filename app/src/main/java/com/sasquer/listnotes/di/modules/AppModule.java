package com.sasquer.listnotes.di.modules;

import android.app.Application;
import android.content.Context;

import com.sasquer.listnotes.ui.main.ActionBarBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    ActionBarBuilder provideActionBar() {
        return new ActionBarBuilder();
    }
}
