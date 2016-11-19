package com.sahil.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sahil on 19/6/16.
 */
public class HistoryIntentService extends IntentService {

    public HistoryIntentService(){
        super(HistoryIntentService.class.getName());
    }

    public HistoryIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HistoryTaskService historyTaskService = new HistoryTaskService(getApplicationContext());
        Bundle args = intent.getExtras();
        historyTaskService.onRunTask(new TaskParams("symbolData", args));
    }
}
