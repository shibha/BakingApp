package com.udacity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.udacity.R;
import com.udacity.fragment.RecipeStepVideoFragment;
import com.udacity.model.Recipe;

public class RecipeStepVideoFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_video);
    }

}
