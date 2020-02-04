package com.sasquer.listnotes.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.sasquer.listnotes.R;
import com.sasquer.listnotes.base.BaseActivity;
import com.sasquer.listnotes.di.viewmodel.ViewModelFactory;
import com.sasquer.listnotes.utils.KeyboardUtil;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IActionBarBuilder {
    @Inject
    ViewModelFactory viewModelFactory;
    private MainViewModel mViewModel;
    private NavController navController;
    private Toolbar mToolbar;
    private MenuItem searchItem;
    private SearchView searchViewAndroidActionBar;

    //toolbar customization
    private boolean isShowCheckIcon = false;
    private boolean isShowSearchIcon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewModel.getOnSearchItemClicked().observe(this, aBoolean -> {
            if (aBoolean)
                onSearchItemClick();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public void onBackPressed() {
////        int id = navController.getCurrentDestination().getId();
////        CharSequence label = navController.getCurrentDestination().getLabel();
////        Log.d("TAG", id + "");
////        Log.d("TAG", label + "");
////        switch (id) {
////            case R.id.settingsFragment:
////            case R.id.customToolbarFragment:
////                finish();
////                break;
////            default:
//        super.onBackPressed();
////        navController.popBackStack();
////                break;
////        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.item_check).setVisible(isShowCheckIcon);
        searchItem = menu.findItem(R.id.item_search);
        searchItem.setVisible(isShowSearchIcon);
        searchViewAndroidActionBar = (SearchView) searchItem.getActionView();
        searchViewAndroidActionBar.setQueryHint("Enter note title");
        LinearLayout searchEditFrame = (LinearLayout) searchViewAndroidActionBar.findViewById(R.id.search_bar);
        ((LinearLayoutCompat.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 0;
        ((LinearLayoutCompat.LayoutParams) searchEditFrame.getLayoutParams()).rightMargin = -50;
        LinearLayout searchf = (LinearLayout) searchViewAndroidActionBar.findViewById(R.id.search_edit_frame);
        ((LinearLayout.LayoutParams) searchf.getLayoutParams()).leftMargin = 0;

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        KeyboardUtil.hideKeyboard(this);
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.item_search:
                mViewModel.setOnSearchItemClicked(true);
                break;
            case R.id.item_check:
                mViewModel.setSaveItemClicked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSearchItemClick() {
        new Handler().postDelayed(() -> {
            TransitionSet set = new TransitionSet()
//                        .addTransition(new Scale(0.7f))
                    .addTransition(new Fade())
                    .setInterpolator(new LinearOutSlowInInterpolator());
            TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.toolbar), set);
            searchItem.expandActionView();
            searchViewAndroidActionBar.setQuery(mViewModel.getSearchField().getValue(), false);
            searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchViewAndroidActionBar.clearFocus();
                    mViewModel.setOnSearchItemClicked(false);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mViewModel.setSearchField(newText);
                    return true;
                }
            });
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    mViewModel.setOnSearchItemClicked(false);
                    return true;
                }
            });
        }, 500);
    }

    @Override
    public void setBackArrow(boolean isBackEnabled) {
        if (isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void setToolbarTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void setToolbarVisible(boolean visible) {

    }

    @Override
    public void showCheckItem(boolean showCheck) {
        isShowCheckIcon = showCheck;
    }

    @Override
    public void showSearchItem(boolean showSearch) {
        isShowSearchIcon = showSearch;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar.setVisibility(View.GONE);
            setSupportActionBar(toolbar);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(mToolbar);
        }
    }
}
