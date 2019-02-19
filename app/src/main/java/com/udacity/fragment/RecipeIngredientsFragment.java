package com.udacity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.R;
import com.udacity.model.Recipe;
import com.udacity.utils.UIUtils;

public class RecipeIngredientsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container,
                false);
        String key = getActivity().getString(R.string.recipe_selected);
        Recipe recipe = getActivity().getIntent().getParcelableExtra(key);
        TextView ingredientText  = view.findViewById(R.id.ingredients_text);
        ingredientText.setText(UIUtils.combineRecipeIngrdients(recipe));
        return view;
    }
}
