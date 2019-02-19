package com.udacity.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.udacity.R;
import com.udacity.adapter.RecipeListViewRecycleAdapter;
import com.udacity.model.Recipe;
import com.udacity.testingutils.SimpleIdlingResource;
import com.udacity.viewmodel.RecipesViewModel;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListViewRecycleAdapter recipeListRecycleViewAdapter;
    private RecyclerView recipeRecycleView;
    private int spanCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        final int orientation = getResources().getConfiguration().orientation;

        if(recipeRecycleView != null && orientation != -1){
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                spanCount = 1;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                spanCount = 3;
            }
            ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(spanCount);
        }

        RecipesViewModel model =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, data -> {
            setRecycleView(data);
        });



    }

    @Override
    protected void onPause() {
        int index = -1;
        int top = -1;
        int spinnerSelection = -1;
        super.onPause();
        if (recipeRecycleView != null) {
            index = ((GridLayoutManager) recipeRecycleView.getLayoutManager()).findFirstVisibleItemPosition();
            View v = recipeRecycleView.getChildAt(0);
            top = (v == null) ? 0 : (v.getTop() - recipeRecycleView.getPaddingTop());
        }

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("index", index);
        editor.putInt("top", top);
        editor.putInt("currentSpinnerSelection", spinnerSelection);
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int index = preferences.getInt("index", -1);
        int top = preferences.getInt("top", -1);
        final int orientation = getResources().getConfiguration().orientation;

        if (recipeRecycleView != null && index != -1 && top != -1) {
            ((GridLayoutManager) recipeRecycleView.getLayoutManager()).scrollToPositionWithOffset(index, top);
        }

        if(recipeRecycleView != null && orientation != -1){
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                spanCount = 1;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                spanCount = 3;
            }
            ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(spanCount);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
    }

    private void setRecycleView(List<Recipe> recipes) {

        if (recipes == null || recipes.size() == 0) {
//TO-DO            setContentView(R.layout.error);
            return;
        }
        recipeRecycleView = findViewById(R.id.recipe_list_recycle_view);
        recipeRecycleView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(),
                spanCount));
        recipeListRecycleViewAdapter = new RecipeListViewRecycleAdapter(this, recipes);
        recipeRecycleView.setAdapter(recipeListRecycleViewAdapter);
    }

    private SimpleIdlingResource mSimpleIdlingResource;

    @NonNull
    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
        }

        return mSimpleIdlingResource;
    }

}
