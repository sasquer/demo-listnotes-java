package com.sasquer.listnotes.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sasquer.listnotes.interactors.RequestsInteractor;
import com.sasquer.listnotes.interactors.ToolbarActionsInteractor;
import com.sasquer.listnotes.utils.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel implements LifecycleOwner {
    private final MutableLiveData<String> showMessageToast = new MutableLiveData<>();
    protected CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    RequestsInteractor requestsInteractor;
    @Inject
    ToolbarActionsInteractor toolbarInteractor;
    private LifecycleRegistry lifecycle = new LifecycleRegistry(this);

    protected BaseViewModel() {

    }

    public BaseViewModel(ToolbarActionsInteractor toolbarInteractor) {
        this.toolbarInteractor = toolbarInteractor;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        lifecycle.markState(Lifecycle.State.DESTROYED);
        disposable.clear();
    }

    public MutableLiveData<String> getShowMessageToast() {
        return showMessageToast;
    }

    public void setShowMessageToast(String message) {
        showMessageToast.setValue(message);
    }

    public MutableLiveData<Boolean> isProgressing() {
        return requestsInteractor.isProgressing();
    }

    public void setProgressing(boolean value) {
        requestsInteractor.setProgressing(value);
    }

    public SingleLiveEvent<Boolean> isSaveItemClicked() {
        return toolbarInteractor.isSaveItemClicked();
    }

    public void setSaveItemClicked(boolean value) {
        toolbarInteractor.setSaveItemClicked(value);
    }
}
