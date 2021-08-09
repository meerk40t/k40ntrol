package org.meerk40t.k40ntrol.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HostViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Serve as host device...");
    }

    public LiveData<String> getText() {
        return mText;
    }
}