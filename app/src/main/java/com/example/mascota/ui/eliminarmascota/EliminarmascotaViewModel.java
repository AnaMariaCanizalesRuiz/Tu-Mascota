package com.example.mascota.ui.eliminarmascota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EliminarmascotaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EliminarmascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is eliminar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}