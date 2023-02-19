package com.example.mascota.ui.agregarmascota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgregarmascotaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AgregarmascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Agregar mascota");
    }

    public LiveData<String> getText() {
        return mText;
    }
}