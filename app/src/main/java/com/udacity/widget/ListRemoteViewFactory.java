package com.udacity.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.udacity.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

public class  ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<String> instructions;
    private Intent intent;

    public ListRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("widget", MODE_PRIVATE);

        final Map<String, ?> widgetIds = sharedPreferences.getAll();
        instructions = new ArrayList<>();
        for(Map.Entry<String, ?> entry : widgetIds.entrySet()) {
            String key = entry.getKey();
            if (key.equals(intent.getStringExtra("id"))) {
                String instructionStr =  (String) entry.getValue();
                String[] instructionsArr = instructionStr.split(key);
                for(String instr : instructionsArr){
                    instructions.add(instr);
                }
            }
        }
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return instructions == null ? 0 : instructions.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);

        String currentInstruction = instructions.get(position);
        views.setTextViewText(R.id.widget_instruction, currentInstruction);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
