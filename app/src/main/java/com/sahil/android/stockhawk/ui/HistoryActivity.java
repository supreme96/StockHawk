package com.sahil.android.stockhawk.ui;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sahil.android.stockhawk.service.HistoryIntentService;
import com.sahil.android.stockhawk.service.HistoryTaskService;

/**
 * Created by sahil on 14/6/16.
 */
public class HistoryActivity extends AppCompatActivity {

    Intent mServiceIntent  = new Intent(this, HistoryIntentService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bund;
        bund = getIntent().getExtras();
        mServiceIntent.putExtras(bund);
        startService(mServiceIntent);
    }
}
