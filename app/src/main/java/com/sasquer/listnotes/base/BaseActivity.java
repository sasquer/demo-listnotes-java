package com.sasquer.listnotes.base;

import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseActivity extends DaggerAppCompatActivity {
    ActionBar actionbar;

    public void setBackArrowActionbar(boolean isShow, String title) {
        actionbar = getSupportActionBar();
        if (isShow) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        } else
            actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setTitle(title);
    }

    protected void setFullScreenMode(boolean isFullScreen) {
        if (isFullScreen)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
