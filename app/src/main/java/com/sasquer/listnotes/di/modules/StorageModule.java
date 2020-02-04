package com.sasquer.listnotes.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.data.IDataSourceManager;
import com.sasquer.listnotes.data.local.db.AppDatabase;
import com.sasquer.listnotes.data.local.db.DataBaseHelper;
import com.sasquer.listnotes.data.local.db.IDatabaseHelper;
import com.sasquer.listnotes.data.local.prefs.IPreferencesHelper;
import com.sasquer.listnotes.data.local.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class StorageModule {
    @Provides
    @Singleton
    static IDataSourceManager provideDataManager(AppRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    static AppDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    static IDatabaseHelper provideDatabaseHelper(DataBaseHelper dataBaseHelper) {
        return dataBaseHelper;
    }

    @Provides
    @Singleton
    static IPreferencesHelper providePreferencesHelper(PreferencesHelper preferencesHelper) {
        return preferencesHelper;
    }

    @Provides
    @Singleton
    static String providePreferenceName() {
        return "app_pref";
    }

}
