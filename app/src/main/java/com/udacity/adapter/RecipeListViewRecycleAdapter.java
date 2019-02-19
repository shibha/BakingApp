package com.udacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.udacity.activity.RecipeDetailFragmentActivty;
import com.udacity.model.Recipe;
import java.util.List;
import com.udacity.R;
import com.udacity.utils.UIUtils;

public class RecipeListViewRecycleAdapter extends RecyclerView.Adapter<RecipeListViewRecycleAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater mInflater;
    private Context context;

    public RecipeListViewRecycleAdapter(Context context, List<Recipe> data) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_list_item, parent, false);
        int height_of_each_item = UIUtils.calculateNoOfRows(context, recipes.size());
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = height_of_each_item;
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.textView.setText(recipe.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipeDetailFragmentActivty.class);
                intent.putExtra(context.getString(R.string.recipe_selected), recipe);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipe_name_formatted);
        }

    }

}