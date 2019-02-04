package com.udacity.utils;

import com.udacity.model.Ingredient;
import com.udacity.model.Recipe;
import com.udacity.model.Step;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonUtils to parse Recipes DATA JSON string and create Recipes LIST
 */
public class JsonUtils {

    private static final String imgBasePath ="http://image.tmdb.org/t/p/w185//";

    public static List<Recipe> createRecipeList(String json) {
        JSONArray base = null;
        List<Recipe> Recipes = new ArrayList<>();
        if(json == null) {
            return Recipes;
        }
        /**
         * Read the  JSON string
         */
        try {
            base = new JSONArray(json);
            for (int i = 0; i < base.length(); i++) {
                Recipes.add(parseRecipeJSON(base.getJSONObject(i)));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return Recipes;
    }

    private static Recipe parseRecipeJSON(JSONObject recipeJson) {
        Recipe recipe = new Recipe();

        try {
            recipe.setId(recipeJson.getInt("id"));
            recipe.setName(recipeJson.getString("name"));
            recipe.setIngredients(parseRecipeIngredientsJSON(recipeJson.getJSONArray("ingredients")));
            recipe.setSteps(parseRecipeStepsJSON(recipeJson.getJSONArray("steps")));
            recipe.setImage(imgBasePath + recipeJson.getString("image"));
            recipe.setServings(recipeJson.getInt("servings"));
            return recipe;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Ingredient> parseRecipeIngredientsJSON(JSONArray ingredientsJson)
            throws  JSONException{
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientsJson.length(); i++) {
            JSONObject currentIngredientJson = ingredientsJson.getJSONObject(i);
            ingredients.add(new Ingredient(currentIngredientJson.getInt("quantity"),
                    currentIngredientJson.getString("measure"),
                    currentIngredientJson.getString("ingredient")));
        }

        return ingredients;
    }


    private static List<Step> parseRecipeStepsJSON(JSONArray stepsJson)
            throws  JSONException{
        List<Step> steps = new ArrayList<>();

        for (int i = 0; i < stepsJson.length(); i++) {
            JSONObject currentStepJson = stepsJson.getJSONObject(i);
            steps.add(new Step(currentStepJson.getInt("id"),
                    currentStepJson.getString("shortDescription"),
                    currentStepJson.getString("description"),
                    currentStepJson.getString("videoURL"),
                    currentStepJson.getString("thumbnailURL")));
        }

        return steps;
    }


}
