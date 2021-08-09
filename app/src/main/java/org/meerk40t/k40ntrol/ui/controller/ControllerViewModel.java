package org.meerk40t.k40ntrol.ui.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ControllerViewModel extends ViewModel {

    private MutableLiveData<String> status;

    public ControllerViewModel() {
        status = new MutableLiveData<>();
        status.setValue("Control the device connection...");
    }

    public LiveData<String> getText() {
        return status;
    }
}