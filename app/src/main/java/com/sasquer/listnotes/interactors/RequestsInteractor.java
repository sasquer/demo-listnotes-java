package com.sasquer.listnotes.interactors;

import androidx.lifecycle.MutableLiveData;

import com.sasquer.listnotes.data.AppRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RequestsInteractor {
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isProgressing = new MutableLiveData<>();
    private AppRepository mRepository;

    @Inject
    public RequestsInteractor(AppRepository repository) {
        mRepository = repository;
    }

    public MutableLiveData<Boolean> isProgressing() {
        return isProgressing;
    }

    public void setProgressing(boolean value) {
        isProgressing.setValue(value);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.setValue(message);
    }

}
