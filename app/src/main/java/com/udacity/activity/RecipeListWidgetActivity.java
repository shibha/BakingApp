package com.udacity.activity;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.udacity.R;
import com.udacity.adapter.RecipeListViewWidgetRecycleAdapter;
import com.udacity.model.Recipe;
import com.udacity.viewmodel.RecipesViewModel;
import java.util.List;

public class RecipeListWidgetActivity extends AppCompatActivity {

    private RecipeListViewWidgetRecycleAdapter recipeListRecycleViewAdapter;
    private RecyclerView recipeRecycleView;
    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_recipe_list);
        RecipesViewModel model =
                ViewModelProviders.of(this).get(RecipesViewModel.class);
        model.getRecipes().observe(this, data -> {
            setRecycleView(data);
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish();
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
        editor.putInt("orientation", getResources().getConfiguration().orientation);
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int index = -1;
        int top = -1;
        int orientation = -1;
        index = preferences.getInt("index", -1);
        top = preferences.getInt("top", -1);
        orientation = preferences.getInt("orientation", -1);

        if (recipeRecycleView != null && index != -1 && top != -1) {
            ((GridLayoutManager) recipeRecycleView.getLayoutManager()).scrollToPositionWithOffset(index, top);
        }

        if (recipeRecycleView != null && orientation != -1) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(3);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(1);
            }

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Orientation", this.getRequestedOrientation());
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
    }

    private void setRecycleView(List<Recipe> recipes) {

        if (recipes == null || recipes.size() == 0) {
//To-Do            return;
        }
        recipeRecycleView = findViewById(R.id.recipe_list_recycle_view);
        recipeRecycleView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(),
                1));
        recipeListRecycleViewAdapter = new RecipeListViewWidgetRecycleAdapter(this, recipes, widgetId);
        recipeRecycleView.setAdapter(recipeListRecycleViewAdapter);
    }

    public  void finishWidget(){
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK);
        finish();
    }

}
