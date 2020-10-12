package com.badas.badassolution.ui.placeholder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaceholderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlaceholderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a placeholder screen");
    }

    public LiveData<String> getText() {
        return mText;
    }
}