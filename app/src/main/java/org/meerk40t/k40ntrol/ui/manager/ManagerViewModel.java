package org.meerk40t.k40ntrol.ui.manager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManagerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManagerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Manage Files for Sending...");
    }

    public LiveData<String> getText() {
        return mText;
    }
}