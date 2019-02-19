package com.udacity.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class InstructionsListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
