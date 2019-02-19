package com.udacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.udacity.R;
import com.udacity.activity.RecipeDetailFragmentActivty;
import com.udacity.activity.RecipeStepVideoFragmentActivity;
import com.udacity.fragment.RecipeStepVideoFragment;
import com.udacity.model.Step;
import com.udacity.utils.UIUtils;

import java.util.List;
import java.util.logging.Logger;

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
        int height_of_each_item = UIUtils.calculateNoOfRows(context, steps.size());
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
                String videoPath = step.getVideoURL();
                String stepDescString = step.getDescription();
                if (videoPath != null && videoPath.trim().length() > 0) {

                    SimpleExoPlayerView videoView = ((RecipeDetailFragmentActivty) context).findViewById(R.id.step_video);
                    TextView stepDesc = ((RecipeDetailFragmentActivty) context).findViewById(R.id.step_desc);
                    if (videoView != null && stepDesc != null) {

                        /**
                         * * its a landscape
                         * */

                        Bundle arguments = new Bundle();
                        arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_VIDEO_URL, videoPath);
                        arguments.putString(RecipeStepVideoFragment.ARG_RECIPE_STEP_DESC, stepDescString);
                        RecipeStepVideoFragment fragment = new RecipeStepVideoFragment();
                        fragment.setArguments(arguments);
                        ((RecipeDetailFragmentActivty) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_step_video_fragment, fragment)
                                .commit();
                    } else {

                        Intent intent = new Intent(view.getContext(), RecipeStepVideoFragmentActivity.class);
                        intent.putExtra(context.getString(R.string.video_url), videoPath);
                        intent.putExtra(context.getString(R.string.step_instruction), stepDescString);
                        context.startActivity(intent);
                    }
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