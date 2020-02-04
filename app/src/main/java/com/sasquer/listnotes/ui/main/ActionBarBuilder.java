package com.sasquer.listnotes.ui.main;

import android.content.Context;

import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActionBarBuilder {

    private boolean isGoBack = false;
    private boolean isToolbarVisible = true;
    private boolean showSearchItem = false;
    private boolean showCheckItem = false;
    private Toolbar toolbar;
    private CharSequence title = "";

    @Inject
    public ActionBarBuilder() {
    }

    public ActionBarBuilder setToolbarVisible(boolean isToolbarVisible) {
        this.isToolbarVisible = isToolbarVisible;
        return this;
    }

    public ActionBarBuilder setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public ActionBarBuilder setShowSearchItem(boolean showSearchItem) {
        this.showSearchItem = showSearchItem;
        return this;
    }

    public ActionBarBuilder setShowCheckItem(boolean showCheckItem) {
        this.showCheckItem = showCheckItem;
        return this;
    }

    public ActionBarBuilder setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        return this;
    }

    public ActionBarBuilder setBackArrow(boolean isGoBack) {
        this.isGoBack = isGoBack;
        return this;
    }

    public void build(Context context) {
        MainActivity main = (MainActivity) context;
//        main.setToolbarVisible(isToolbarVisible);
        main.setToolbar(toolbar);
        main.showCheckItem(showCheckItem);
        main.showSearchItem(showSearchItem);
        main.setToolbarTitle(title);
        main.setBackArrow(isGoBack);
        main.invalidateOptionsMenu();
    }
}
