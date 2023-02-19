package com.example.mascota.ui.vermascotas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VermascotasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VermascotasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ver Mascotas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


