package com.lbelmar.terrenoeco.ui.mapa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("fragment del mapa");
    }

    public LiveData<String> getText() {
        return mText;
    }
}