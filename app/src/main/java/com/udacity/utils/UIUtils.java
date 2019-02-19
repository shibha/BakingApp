package com.udacity.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.udacity.model.Ingredient;
import com.udacity.model.Recipe;

public class UIUtils {
    public static int calculateNoOfRows(Context context, int size) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        int recipeListSize = size > 0 ? size : 1;
        int heightOfEachRow = (int) (dpHeight / recipeListSize);
        return heightOfEachRow;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int calculateNoOfColumns(Context context, int size) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int recipeListSize = size > 0 ? size : 0;
        int noOfRows = (int) (dpWidth / recipeListSize);
        return noOfRows;
    }

    public static String combineRecipeIngrdients(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        if (isRecipeAndIngredientsExist(recipe)) {
            int i = 1;
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient != null) {
                    sb.append(String.valueOf(i) + ". " +
                            ingredient.getIngredient().substring(0,1).toUpperCase()+
                            ingredient.getIngredient().substring(1) + ", ");
                    sb.append("Measure : " + ingredient.getMeasure().toLowerCase() + ", ");
                    sb.append("Quantity : " + ingredient.getQuantity() + "\n");
                    i++;
                }
            }
        }
        return sb.toString();
    }

    private static boolean isRecipeAndIngredientsExist(Recipe recipe) {
        return recipe != null && recipe.getIngredients() != null && recipe.getIngredients().size() > 0;
    }

}
