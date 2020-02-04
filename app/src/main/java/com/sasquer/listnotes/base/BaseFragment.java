package com.sasquer.listnotes.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.sasquer.listnotes.R;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {
    ActionBar actionbar;
    Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar != null ? toolbar : (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    protected void setToolbarVisibility(boolean isVisible) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
