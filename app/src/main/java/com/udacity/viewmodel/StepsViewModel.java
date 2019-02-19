package com.udacity.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.udacity.model.Step;

import java.util.List;

public class StepsViewModel extends AndroidViewModel {

    private final List<Step> steps;

    public StepsViewModel(Application application, List<Step> steps) {
        super(application);
        this.steps = steps;
    }

}
