package com.udacity.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.udacity.model.Recipe;
import com.udacity.utils.JsonUtils;
import com.udacity.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AsyncRecipesDataLoader extends AsyncTaskLoader<List<Recipe>> {

    private URL searchUrl;

    public AsyncRecipesDataLoader(Context context, URL searchUrl) {
        super(context);
        this.searchUrl = searchUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
        if (searchUrl == null) {
            return null;
        }

        try {
            return JsonUtils.createRecipeList(NetworkUtils.getResponseFromHttpUrl(searchUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
