package com.example.mascota.ui.vacunacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VacunacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VacunacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is vacunacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}