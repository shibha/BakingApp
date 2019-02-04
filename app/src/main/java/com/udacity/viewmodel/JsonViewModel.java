package com.udacity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.udacity.model.Recipe;
import java.util.List;

public class JsonViewModel extends AndroidViewModel {

    private final JsonLiveData data;

    public JsonViewModel(Application application) {
        super(application);
        data = new JsonLiveData(application);
    }

    public LiveData<List<Recipe>> getData() {
        return data;
    }
}