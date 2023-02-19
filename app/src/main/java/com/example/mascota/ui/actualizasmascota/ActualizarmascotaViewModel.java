package com.example.mascota.ui.actualizasmascota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActualizarmascotaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public void ActualizarmascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is actualizarmascota fragment");
    }

    public ActualizarmascotaViewModel(MutableLiveData<String> mText) {
        this.mText = mText;
    }

    public LiveData<String> getText() {
        return mText;
    }



}
