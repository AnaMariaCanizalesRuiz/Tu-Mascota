package com.example.mascota.ui.desparasitacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesparacitacionViewModel extends ViewModel {

        private final MutableLiveData<String> mText;

    public DesparacitacionViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is desparasitacion fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }



}
