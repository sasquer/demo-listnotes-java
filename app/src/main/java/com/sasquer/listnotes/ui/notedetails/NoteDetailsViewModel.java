package com.sasquer.listnotes.ui.notedetails;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.base.BaseViewModel;
import com.sasquer.listnotes.data.AppRepository;
import com.sasquer.listnotes.data.local.db.entity.Note;
import com.sasquer.listnotes.data.local.db.entity.NoteCheckItem;
import com.sasquer.listnotes.interactors.ToolbarActionsInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteDetailsViewModel extends BaseViewModel {
    private static final String TAG = "NoteDetailsViewModel";
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showConfirmDialog = new MutableLiveData<>();
    private final MutableLiveData<Boolean> confirmBack = new MutableLiveData<>();
    private final MediatorLiveData<List<NoteCheckItem>> checkItems = new MediatorLiveData<>();
    private Note currentNote = null;
    private AppRepository mRepository;

    @Inject
    public NoteDetailsViewModel(AppRepository mAppRepository,
                                ToolbarActionsInteractor toolbarInteractor,
                                NoteDetailsFragmentArgs args) {
        super(toolbarInteractor);
        mRepository = mAppRepository;
        int id = args.getNoteId();
        Log.d(TAG, "Init VM " + id);
        if (id != -1) {
            disposable.add(
                    mRepository.getNoteById(id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    note -> {
                                        currentNote = note;
                                        setTitle(currentNote.getTitle());
                                        mRepository.addCheckItems(currentNote.getCheckList());
                                    }
                            )
            );
        } else {
            mRepository.addCheckItems(new ArrayList<>());
        }
        checkItems.addSource(mAppRepository.observeCheckItems(), checkItems::postValue);

    }

    public MutableLiveData<List<NoteCheckItem>> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<NoteCheckItem> items) {
        mRepository.updateCheckItemsAfterSwipe(items);
    }

    public void addCheckItem(NoteCheckItem item) {
        mRepository.addCheckItem(item);
    }

    public void removeCheckItem(int positiop) {
        mRepository.removeCheckItem(positiop);
    }

    public MutableLiveData<Boolean> isConfirmBack() {
        return confirmBack;
    }

    public void checkInputData() {
        if (currentNote != null) {
            if (currentNote.getTitle().equals(getTitle().getValue()) &&
                    currentNote.getCheckList().equals(checkItems.getValue())) {
                confirmBack.setValue(true);
            } else {
                setShowConfirmDialog(true);
            }
        } else {
            if (getTitle().getValue() != null && !getTitle().getValue().isEmpty() ||
                    checkItems.getValue() != null && !checkItems.getValue().isEmpty()) {
                setShowConfirmDialog(true);
            } else
                confirmBack.setValue(true);
        }
    }

    public void saveNoteToDatabase(List<NoteCheckItem> list) {
        if (currentNote == null) {
            Note _note = new Note(title.getValue());
            _note.setCheckList(list);
            mRepository.insertNote(_note);
        } else {
            currentNote.setTitle(title.getValue());
            currentNote.setCheckList(list);
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