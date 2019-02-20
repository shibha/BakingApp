package com.udacity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.R;
import com.udacity.constants.Constants;
import com.udacity.fragment.RecipeStepVideoFragment;


public class RecipeStepVideoFragmentActivity extends FragmentActivity {
    private SimpleExoPlayerView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_video);
        videoView = findViewById(R.id.step_video);
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
