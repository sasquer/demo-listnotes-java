package com.sasquer.listnotes.di.component;

import android.app.Application;

import com.sasquer.listnotes.base.BaseApplication;
import com.sasquer.listnotes.di.modules.ActivityBindingModule;
import com.sasquer.listnotes.di.modules.AppModule;
import com.sasquer.listnotes.di.modules.StorageModule;
import com.sasquer.listnotes.di.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        StorageModule.class,
        ViewModelModule.class,
        ActivityBindingModule.class
})

public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(BaseApplication baseApplication);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}