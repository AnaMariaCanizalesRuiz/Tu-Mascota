package com.example.mascota.ui.cerrarsesion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CerrarsesionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CerrarsesionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Soy cerrar sesion");
    }

    public LiveData<String> getText() {
        return mText;
    }
}




