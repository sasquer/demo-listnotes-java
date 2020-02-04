package com.sasquer.listnotes.base;

import com.sasquer.listnotes.di.component.AppComponent;
import com.sasquer.listnotes.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent component = DaggerAppComponent.builder()
                .application(this)
                .build();
        component.inject(this);

        return component;
    }
}