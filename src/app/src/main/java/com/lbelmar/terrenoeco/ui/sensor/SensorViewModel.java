package com.lbelmar.terrenoeco.ui.sensor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SensorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SensorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment del sensor");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
