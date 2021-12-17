package com.lbelmar.terrenoeco.ui.usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsuarioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UsuarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment del usuario");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
