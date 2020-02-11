package com.sasquer.listnotes.ui.notedetails;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sasquer.listnotes.R;
import com.sasquer.listnotes.base.BaseFragment;
import com.sasquer.listnotes.databinding.FragmentNoteDetailsBinding;
import com.sasquer.listnotes.ui.main.ActionBarBuilder;

import javax.inject.Inject;

public class NoteDetailsFragment extends BaseFragment {

    NavController navController;
    @Inject
    ActionBarBuilder actionBarBuilder;
    @Inject
    NoteDetailsViewModelFactory viewModelFactory;
    private NoteDetailsViewModel mViewModel;
    private FragmentNoteDetailsBinding binding;
    private OnBackPressedCallback callback;
    private OnBackPressedDispatcher onBackPressedDispatcher;
    private CheckItemAdapter mAdapter;

    private AlertDialog dialog;

    @Inject
    public NoteDetailsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(NoteDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        actionBarBuilder
                .setTitle("")
                .setToolbar(null)
                .setBackArrow(true)
                .setShowCheckItem(true)
                .setShowSearchItem(false)
                .build(getActivity());

        setupBackCallback();

        setupRecyclerView();

        mViewModel.getTitle().observe(this, s -> getToolbar().setTitle(s));
        mViewModel.isSaveItemClicked().observe(this, aBoolean -> {
            mViewModel.saveNoteToDatabase(mAdapter.getList());
        });
        mViewModel.isConfirmBack().observe(this, aBoolean -> {
                    callback.remove();
                    onBackPressedDispatcher.onBackPressed();
                }
        );

        mViewModel.getShowConfirmDialog().observe(this, aBoolean -> {
            if (aBoolean)
                showConfirmDialog();
        });

        mViewModel.getCheckItems().observe(this, noteCheckItems -> {
            if (noteCheckItems.isEmpty())
                mAdapter.addEmptyItem();
            else
                mAdapter.setItems(noteCheckItems);
        });
    }

    private void setupBackCallback() {
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mViewModel.checkInputData();
            }
        };
        onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, callback);
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Data was edited!")
                .setMessage("You want to exit without saving changes?")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                            callback.remove();
                            onBackPressedDispatcher.onBackPressed();
                        }
                )
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                .setOnCancelListener(dialogInterface -> mViewModel.setShowConfirmDialog(false));
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.cancel();
    }

    public NoteDetailsFragmentArgs provideArgs() {
        return NoteDetailsFragmentArgs.fromBundle(getArguments());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rvCheckedItem;
        mAdapter = new CheckItemAdapter(mViewModel);
        ItemTouchHelper.Callback callback = new CheckItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}