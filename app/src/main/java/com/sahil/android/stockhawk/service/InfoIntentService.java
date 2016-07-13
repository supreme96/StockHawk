package com.sahil.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sahil on 13/7/16.
 */

public class InfoIntentService extends IntentService {

    public InfoIntentService(){
        super(InfoIntentService.class.getName());
    }

    public InfoIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InfoTaskService infoTaskService = new InfoTaskService(getApplicationContext());
        Bundle args = intent.getExtras();
        infoTaskService.onRunTask(new TaskParams("symbolData", args));
    }
}


