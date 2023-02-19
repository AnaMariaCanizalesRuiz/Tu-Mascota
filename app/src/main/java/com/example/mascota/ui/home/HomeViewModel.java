package com.example.mascota.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public void SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is HOME fragment");
    }

    public HomeViewModel(MutableLiveData<String> mText) {
        this.mText = mText;
    }

    public LiveData<String> getText() {
        return mText;
    }

}