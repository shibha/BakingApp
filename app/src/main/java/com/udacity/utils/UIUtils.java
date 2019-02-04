package com.udacity.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class UIUtils {
    public static int calculateNoOfRows(Context context, int size) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        int recipeListSize = size > 0 ? size : 0;
        int noOfRows = (int) (dpHeight / recipeListSize);
        return noOfRows;
    }

    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        return (int)dpHeight;
    }

    public static int calculateNoOfColumns(Context context, int size) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int recipeListSize = size > 0 ? size : 0;
        int noOfRows = (int) (dpWidth / recipeListSize);
        return noOfRows;
    }

}
