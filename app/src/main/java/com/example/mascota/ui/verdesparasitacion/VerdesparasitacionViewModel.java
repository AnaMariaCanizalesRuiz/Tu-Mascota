package com.example.mascota.ui.verdesparasitacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VerdesparasitacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VerdesparasitacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ver desparasitaciones fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}