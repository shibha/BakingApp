package com.udacity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.FileObserver;
import com.udacity.model.Recipe;
import com.udacity.utils.JsonUtils;
import com.udacity.utils.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JsonLiveData extends LiveData<List<Recipe>> {

    private final Context context;
    private final FileObserver fileObserver;

    public JsonLiveData(Context context) {
        this.context = context;
        fileObserver = new FileObserver(NetworkUtils.getPath()) {
            @Override
            public void onEvent(int event, String path) {
                loadData();
            }
        };
        loadData();
    }

    @Override
    protected void onActive() {
        fileObserver.startWatching();
    }
    @Override
    protected void onInactive() {
        fileObserver.stopWatching();
    }

    private void loadData() {
        new AsyncTask<Void,Void,List<Recipe>>() {
            @Override
            protected List<Recipe> doInBackground(Void... voids) {
                URL searchUrl = NetworkUtils.buildUrl();
                if (searchUrl == null) {
                    return null;
                }

                try {
                    return JsonUtils.createRecipeList(NetworkUtils.getResponseFromHttpUrl(
                            searchUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(List<Recipe> data) {
                setValue(data);
            }
        }.execute();
    }
}
