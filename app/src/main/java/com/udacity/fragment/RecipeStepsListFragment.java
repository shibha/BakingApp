package com.udacity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udacity.R;
import com.udacity.adapter.RecipeStepListViewRecycleAdapter;
import com.udacity.model.Recipe;
import com.udacity.model.Step;

import java.util.List;

public class RecipeStepsListFragment extends Fragment {
    
    private RecyclerView recipeStepRecycleView;
    private RecipeStepListViewRecycleAdapter recipeStepListViewRecycleAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        String key = getActivity().getString(R.string.recipe_selected);
        Recipe recipe = getActivity().getIntent().getParcelableExtra(key);
        setRecycleView(recipe.getSteps(), view);
        return view;
    }

    private void setRecycleView(List<Step> steps, View view) {

        if (steps == null || steps.size() == 0) {
            return;
        }
        recipeStepRecycleView = view.findViewById(R.id.recipe_detail_steps_list_view);

        recipeStepRecycleView.setLayoutManager(new GridLayoutManager(this.getActivity().
                getApplicationContext(),
                1));
        recipeStepListViewRecycleAdapter = new RecipeStepListViewRecycleAdapter(getActivity(), steps);
        recipeStepRecycleView.setAdapter(recipeStepListViewRecycleAdapter);
    }
}
