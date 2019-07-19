package com.aleson.example.nasaapodapp.feature.profile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLoading(boolean value) {
        this.loading.setValue(value);
    }
}
