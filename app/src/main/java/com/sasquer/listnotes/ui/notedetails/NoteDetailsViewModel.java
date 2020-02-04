package com.sasquer.listnotes.ui.notedetails;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.base.BaseViewModel;
import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.interactors.ToolbarActionsInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteDetailsViewModel extends BaseViewModel {
    private static final String TAG = "NoteDetailsViewModel";
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MediatorLiveData<Note> currNote = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> showConfirmDialog = new MutableLiveData<>();
    private final MutableLiveData<Boolean> confirmBack = new MutableLiveData<>();
    private Note currentNote = null;
    private AppRepository mRepository;

    @Inject
    public NoteDetailsViewModel(AppRepository mAppRepository,
                                ToolbarActionsInteractor toolbarInteractor,
                                NoteDetailsFragmentArgs args) {
        super(toolbarInteractor);
//        setProgressing(true);
        mRepository = mAppRepository;
        int id = args.getNoteId();
        Log.d(TAG, "NoteDetailsViewModel: init VM " + id);
        if (id != -1) {
            disposable.add(
                    mRepository.getNoteById(id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    note -> {
                                        currentNote = note;
                                        setTitle(currentNote.getTitle());
                                    }
                            )
            );
        }

//        currentNote = Transformations.switchMap(getNoteId(), input -> {
//            if (input == -1) {
//                title.setValue("");
//                return new MutableLiveData<>(null);
//            } else
//                return mRepository.getNoteById(input);
//        });
//
//        ((MediatorLiveData) currentNote2).addSource(mRepository.getNoteById(1), new Observer<Note>() {
//            @Override
//            public void onChanged(Note note) {
//                ((MediatorLiveData<Note>) currentNote2).setValue(note);
//            }
//        });

//        if (getNoteId().getValue() != -1) {
////            title.setValue("");
//            currentNote = new MutableLiveData<>(null);
//        } else
//        new Handler().postDelayed(() -> setProgressing(false), 2000);
    }

    public MutableLiveData<Boolean> isConfirmBack() {
        return confirmBack;
    }

    public void checkInputData() {
        if (currentNote != null) {
            if (currentNote.getTitle().equals(getTitle().getValue())) {
                confirmBack.setValue(true);
            } else {
                setShowConfirmDialog(true);
            }
        } else {
            confirmBack.setValue(true);
        }
    }

    public void saveNoteToDatabase() {
        if (currentNote == null) {
            Note _note = new Note(title.getValue());
            mRepository.insertNote(_note);
        } else {
            currentNote.setTitle(title.getValue());
            mRepository.insertNote(currentNote);
        }
        confirmBack.setValue(true);
        title.setValue("");
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String value) {
        title.setValue(value);
    }

    public MutableLiveData<Boolean> getShowConfirmDialog() {
        return showConfirmDialog;
    }

    public void setShowConfirmDialog(Boolean value) {
        showConfirmDialog.setValue(value);
    }
}