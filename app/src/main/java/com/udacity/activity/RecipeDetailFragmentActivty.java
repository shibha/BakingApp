package com.udacity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.R;
import com.udacity.constants.Constants;
import com.udacity.fragment.RecipeStepVideoFragment;
import com.udacity.model.Recipe;
import com.udacity.utils.UIUtils;

public class RecipeDetailFragmentActivty extends AppCompatActivity {

    private SimpleExoPlayerView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Recipe current_recipe = getIntent().getParcelableExtra(getString(R.string.recipe_selected));
        setSupportActionBar(toolbar);
        if (current_recipe != null) {
            toolbar.setTitle(current_recipe.getName());
        } else {
            toolbar.setTitle(getString(R.string.app_name));
        }

        TextView ingredientsView = findViewById(R.id.ingredients_text);
        TextView ingredientsLabelView = findViewById(R.id.ingredients_label);
        TextView stepsLabelView = findViewById(R.id.steps_label);
        videoView = findViewById(R.id.step_video);


        ImageView imageView = findViewById(R.id.step_thumbnail);
        RecyclerView stepsView = findViewById(R.id.recipe_detail_steps_list_view);
        LinearLayout.LayoutParams ingredientsLayoutParams = (LinearLayout.LayoutParams) ingredientsView.getLayoutParams();
        LinearLayout.LayoutParams ingredientsLabelViewParams = (LinearLayout.LayoutParams) ingredientsLabelView.getLayoutParams();
        LinearLayout.LayoutParams stepsLabelViewParams = (LinearLayout.LayoutParams) stepsLabelView.getLayoutParams();
        ingredientsLabelViewParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        ingredientsLabelView.setLayoutParams(ingredientsLabelViewParams);
        stepsLabelViewParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        stepsLabelView.setLayoutParams(stepsLabelViewParams);
        int screenWidth = UIUtils.getScreenWidth(RecipeDetailFragmentActivty.this);
        int miscHeight = stepsLabelViewParams.height + ingredientsLabelViewParams.height;
        int heightLeft = UIUtils.getScreenHeight(RecipeDetailFragmentActivty.this) - miscHeight;
        if (videoView != null) {
            screenWidth = screenWidth / 2;
            LinearLayout.LayoutParams videoParams = (LinearLayout.LayoutParams) videoView.getLayoutParams();
            videoParams.width = screenWidth;
            videoView.setLayoutParams(videoParams);
            final int heightIngredient = heightLeft / 3;
            ingredientsLayoutParams.height = heightIngredient;
            ingredientsView.setLayoutParams(ingredientsLayoutParams);
            final int heightSteps = (heightLeft * 3) / 6;
            if (stepsView != null) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stepsView.getLayoutParams();
                params.height = heightSteps;
                stepsView.setLayoutParams(params);
            }
        } else {
            if (imageView != null) {
                screenWidth = screenWidth / 2;
                LinearLayout.LayoutParams videoParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                videoParams.width = screenWidth;
                imageView.setLayoutParams(videoParams);
                final int heightIngredient = heightLeft / 3;
                ingredientsLayoutParams.height = heightIngredient;
                ingredientsView.setLayoutParams(ingredientsLayoutParams);
                final int heightSteps = (heightLeft * 3) / 6;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stepsView.getLayoutParams();
                params.height = heightSteps;
                stepsView.setLayoutParams(params);
            } else {
                final int heightHalf = heightLeft / 2;
                ingredientsLayoutParams.height = heightHalf;
                ingredientsView.setLayoutParams(ingredientsLayoutParams);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stepsView.getLayoutParams();
                params.height = heightHalf;
                stepsView.setLayoutParams(params);
            }
        }
        ingredientsView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (videoView != null && videoView.getPlayer() != null) {
            SimpleExoPlayer exoPlayer = videoView.getPlayer();
            outState.putLong(Constants.SEEK_POSITON, exoPlayer.getCurrentPosition());
            outState.putBoolean(Constants.IS_PLAYING, exoPlayer.getPlayWhenReady());
        }
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
    }

}
