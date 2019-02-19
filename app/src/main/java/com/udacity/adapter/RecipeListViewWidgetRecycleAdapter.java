package com.udacity.adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.udacity.R;
import com.udacity.activity.RecipeListWidgetActivity;
import com.udacity.model.Recipe;
import com.udacity.model.Step;
import com.udacity.utils.UIUtils;
import com.udacity.widget.InstructionsListWidgetService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

public class RecipeListViewWidgetRecycleAdapter extends RecyclerView.Adapter<RecipeListViewWidgetRecycleAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater mInflater;
    private Context context;
    private int widgetId;

    public RecipeListViewWidgetRecycleAdapter(Context context, List<Recipe> data, int widgetId) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = data;
        this.context = context;
        this.widgetId = widgetId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_list_item, parent, false);
        int height_of_each_item = UIUtils.calculateNoOfRows(context, recipes.size());
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = height_of_each_item;
        view.setLayoutParams(params);
        view.setMinimumHeight(height_of_each_item);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.textView.setText(recipe.getName());
        int height_of_each_item = UIUtils.calculateNoOfRows(context, recipes.size());
        holder.textView.setHeight(height_of_each_item);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting an instance of WidgetManager
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                StringBuilder widgetInstructions = new StringBuilder();

                for (Step step : recipe.getSteps()) {
                    if (step != null && step.getDescription() != null &&
                            step.getDescription().trim().length() > 0 ) {
                        widgetInstructions.append(step.getDescription() + "widget-" + widgetId);
                    }

                }
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
                views.setTextViewText(R.id.appwidget_text, recipe.getName());

                Intent intent = new Intent(context, InstructionsListWidgetService.class);
                intent.putExtra("id", "widget-" + widgetId);
                views.setRemoteAdapter(R.id.appwidget_ingredient_list, intent);
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(widgetId, views);

                SharedPreferences sharedPreferences = context.getSharedPreferences("widget",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(String.valueOf("widget-" + widgetId), widgetInstructions.toString());
                editor.commit();

                ((RecipeListWidgetActivity) context).finishWidget();

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