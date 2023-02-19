package com.example.mascota.ui.vervacunacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VervacunacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VervacunacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is vervacunacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}