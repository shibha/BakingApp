package com.udacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.R;
import com.udacity.activity.RecipeDetailFragmentActivty;
import com.udacity.activity.RecipeStepVideoFragmentActivity;
import com.udacity.constants.Constants;
import com.udacity.fragment.RecipeStepVideoFragment;
import com.udacity.model.Step;

import java.util.List;

public class RecipeStepListViewRecycleAdapter extends RecyclerView.Adapter<RecipeStepListViewRecycleAdapter.ViewHolder> {

    private List<Step> steps;
    private LayoutInflater mInflater;
    private Context context;

    public RecipeStepListViewRecycleAdapter(Context context, List<Step> data) {
        this.mInflater = LayoutInflater.from(context);
        this.steps = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_step_list_item, parent, false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = GridLayoutManager.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = steps.get(position);
        holder.textView.setText(step.getDescription());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView stepDescView = ((RecipeDetailFragmentActivty) context).findViewById(R.id.step_desc);
                String stepDescString = step.getDescription();
                String videoPath = step.getVideoURL();
                String imagePath = step.getThumbnailURL();
                if (stepDescView != null && stepDescString != null && stepDescString.trim().length() > 0) {

                    if (videoPath != null && videoPath.trim().length() > 0) {
                        Bundle arguments = new Bundle();
                        arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_VIDEO_URL, videoPath);
                        arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_STEP_DESC, stepDescString);
                        RecipeStepVideoFragment fragment = new RecipeStepVideoFragment();
                        fragment.setArguments(arguments);
                        ((RecipeDetailFragmentActivty) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_video_fragment, fragment)
                                .commit();
                    } else {
                        Bundle arguments = new Bundle();
                        if (imagePath != null && imagePath.trim().length() > 0) {
                            arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_IMAGE_URL, imagePath);
                        } else {
                            arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_IMAGE_URL, Constants.DEFAULT);
                        }
                        arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_STEP_DESC, stepDescString);
                        RecipeStepVideoFragment fragment = new RecipeStepVideoFragment();
                        fragment.setArguments(arguments);
                        ((RecipeDetailFragmentActivty) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_video_fragment, fragment)
                                .commit();
                    }
                } else {
                    Intent intent = new Intent(view.getContext(), RecipeStepVideoFragmentActivity.class);
                    if (videoPath != null && videoPath.trim().length() > 0) {
                        intent.putExtra(context.getString(R.string.video_url), videoPath);
                    } else {
                        if (imagePath != null && imagePath.trim().length() > 0) {
                            intent.putExtra(context.getString(R.string.image_url), imagePath);
                        } else {
                            intent.putExtra(context.getString(R.string.image_url), Constants.DEFAULT);
                        }
                    }
                    intent.putExtra(context.getString(R.string.step_instruction), stepDescString);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipe_step_text);
        }

    }

}