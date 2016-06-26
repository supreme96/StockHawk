package com.sahil.android.stockhawk.ui;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;
import com.sahil.android.stockhawk.service.HistoryIntentService;
import com.sahil.android.stockhawk.service.HistoryTaskService;

/**
 * Created by sahil on 14/6/16.
 */
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mServiceIntent  = new Intent(getApplicationContext(), HistoryIntentService.class);
        Bundle bund;
        bund = getIntent().getExtras();
        mServiceIntent.putExtras(bund);
        startService(mServiceIntent);
    }
}
