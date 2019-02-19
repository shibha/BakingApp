package com.udacity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.udacity.model.Recipe;
import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private final RecipesJsonLiveData recipes;

    public RecipesViewModel(Application application) {
        super(application);
        recipes = new RecipesJsonLiveData(application);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}