package com.sasquer.listnotes.ui.main;

import androidx.appcompat.widget.Toolbar;

public interface IActionBarBuilder {

    void setBackArrow(boolean isBackEnabled);

    void setToolbarTitle(CharSequence title);

    void setToolbarVisible(boolean visible);

    void showCheckItem(boolean showCheck);

    void showSearchItem(boolean showSearch);

    void setToolbar(Toolbar toolbar);
}
