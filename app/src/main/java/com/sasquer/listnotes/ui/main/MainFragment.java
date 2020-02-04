package com.sasquer.listnotes.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sasquer.listnotes.R;
import com.sasquer.listnotes.base.BaseFragment;
import com.sasquer.listnotes.databinding.FragmentMainBinding;
import com.sasquer.listnotes.di.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class MainFragment extends BaseFragment {

    NavController navController;
    @Inject
    ActionBarBuilder actionBarBuilder;
    @Inject
    ViewModelFactory viewModelFactory;
    private ActionBar actionbar;
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;
    private FragmentMainBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        actionBarBuilder
                .setTitle("Notes")
                .setToolbar(null)
                .setBackArrow(false)
                .setShowCheckItem(false)
                .setShowSearchItem(true)
                .build(getActivity());

        mViewModel.isAddClicked().observe(this, aBoolean -> {
            if (aBoolean) {
                MainFragmentDirections.ActionMainFragmentToNoteDetailsFragment action =
                        MainFragmentDirections.actionMainFragmentToNoteDetailsFragment();
                action.setNoteId(-1);
                new Handler().postDelayed(() -> navController.navigate(action), 200);
            }
        });

        mViewModel.getAllNotes().observe(this, notes -> mAdapter.setItems(notes));

        mViewModel.isNeedShowMessage().observe(this, aBoolean
                -> Toast.makeText(getActivity(), mViewModel.getMessageText().getValue(), Toast.LENGTH_SHORT).show());

        setUpRecycler();

        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
    }

    private void setUpRecycler() {
        mAdapter = new NotesAdapter((view, id) -> {
            MainFragmentDirections.ActionMainFragmentToNoteDetailsFragment action =
                    MainFragmentDirections.actionMainFragmentToNoteDetailsFragment();
            action.setNoteId(id);
            new Handler().postDelayed(() -> navController.navigate(action), 200);
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}