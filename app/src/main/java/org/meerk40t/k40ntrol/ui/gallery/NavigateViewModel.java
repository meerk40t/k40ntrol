package org.meerk40t.k40ntrol.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NavigateViewModel extends ViewModel {

    private MutableLiveData<String> status;

    public NavigateViewModel() {
        status = new MutableLiveData<>();
        status.setValue("Adjust Laser Positioning...");
    }

    public LiveData<String> getText() {
        return status;
    }
}