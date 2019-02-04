package com.udacity.activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.arch.lifecycle.ViewModelProviders;
import android.view.View;

import com.udacity.R;
import com.udacity.adapter.RecipeListViewRecycleAdapter;
import com.udacity.model.Recipe;
import com.udacity.utils.UIUtils;
import com.udacity.viewmodel.JsonViewModel;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListViewRecycleAdapter recipeListRecycleViewAdapter;
    private RecyclerView recipeRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.out.println("SHIBHA ON CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        JsonViewModel model =
                ViewModelProviders.of(this).get(JsonViewModel.class);
        model.getData().observe(this, data -> {
            setRecycleView(data);
        });
    }

    @Override
    protected void onPause() {
//        System.out.println("SHIBHA ON PAUSE");
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
        System.out.println("The orientation : SHIBHA " + getResources().getConfiguration().orientation);
        editor.putInt("orientation", getResources().getConfiguration().orientation);
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        System.out.println("SHIBHA ON RESUME");

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

        if(recipeRecycleView != null && orientation != -1){
            System.out.println("SHIBHA ON orientation " + orientation);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(3);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((GridLayoutManager) recipeRecycleView.getLayoutManager()).setSpanCount(1);
            }

        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        View recycle_list_item_view = findViewById(R.id.recipe_list_item);
//        int height_of_each_item = UIUtils.calculateNoOfRows(this, recipeListRecycleViewAdapter.getItemCount());
//        recycle_list_item_view.setMinimumHeight(height_of_each_item);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            recycle_list_item_view.setMinimumWidth(UIUtils.getScreenWidth(this));
//        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            recycle_list_item_view.setMinimumWidth(3);
//        }
//    }

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
//            setContentView(R.layout.error);
            return;
        }
        recipeRecycleView = findViewById(R.id.recipe_list_recycle_view);
        recipeRecycleView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(),
                1));
        recipeListRecycleViewAdapter = new RecipeListViewRecycleAdapter(this, recipes);
        recipeRecycleView.setAdapter(recipeListRecycleViewAdapter);
    }

}
