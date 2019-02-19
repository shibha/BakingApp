package com.udacity.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.udacity.R;
import com.udacity.constants.Constants;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        SharedPreferences sharedPreferences = context.getSharedPreferences("widget", MODE_PRIVATE);

        final Map<String, ?> widgetIds = sharedPreferences.getAll();

        for(Map.Entry<String, ?> entry : widgetIds.entrySet()){

            String key = entry.getKey();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            Intent serviceIntent = new Intent(context, InstructionsListWidgetService.class);

            if(key.startsWith("widget-")){
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
                String widgetId = key.substring(key.indexOf("-") + 1);
                views.setRemoteAdapter(Integer.valueOf(widgetId), R.id.appwidget_ingredient_list, serviceIntent);
                appWidgetManager.updateAppWidget(Integer.valueOf(widgetId), views);
            }
        }
    }

  


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

}

