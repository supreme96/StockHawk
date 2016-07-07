package com.sahil.android.stockhawk.ui;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.android.gms.gcm.TaskParams;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sahil.android.stockhawk.R;
import com.sahil.android.stockhawk.service.HistoryIntentService;
import com.sahil.android.stockhawk.service.HistoryTaskService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.sahil.android.stockhawk.R.id.graph;

/**
 * Created by sahil on 14/6/16.
 */
public class HistoryActivity extends AppCompatActivity {

    ArrayList<String> results;
    GraphView graphView;

/*
    @Override
    protected void onNewIntent(Intent intent) {
        results = intent.getStringArrayListExtra("list");
        drawGraph(results);
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(br, new IntentFilter("historyResults"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
    }

    protected BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = intent.getStringArrayListExtra("list");
            drawGraph();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        graphView = (GraphView) findViewById(graph);
        Intent mServiceIntent  = new Intent(getApplicationContext(), HistoryIntentService.class);
        Bundle bund;
        bund = getIntent().getExtras();
        mServiceIntent.putExtras(bund);
        startService(mServiceIntent);
    }

    public void drawGraph(){
        Float[] close  = new Float[results.size()/3];
        Date [] dates = new Date[results.size()/3];
        DataPoint[] datapoints = new DataPoint[results.size()/3];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        for(int i=0; i<results.size();i+=3){
            close[i/3] = Float.parseFloat(results.get(i+1));
            try {
                date = dateFormat.parse(results.get(i));
                dates[i/3] = date;
            }
            catch (java.text.ParseException e){}
        }
        for(int i=0;i<dates.length;i++){
            datapoints[i] = new DataPoint(i, close[close.length-1-i]);
        }
        for(int i = 0 ; i<datapoints.length;i++){
            Log.v("data parsed", "date : " + dates[dates.length-1-i]+" close :" + close[close.length-1-i]);}

        //GRAPH PROBLEM IS DUE TO HERE

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datapoints);
        /*graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
        graphView.getViewport().setXAxisBoundsManual(false);*/
        //graphView.getViewport().setMinX(dates[0].getTime());
        //graphView.getViewport().setMaxX(dates[dates.length-1].getTime());

        graphView.setTitle("Price over past 30 days");
        graphView.setTitleColor(getResources().getColor(R.color.white));
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {" 30 days ago ", " 15 days ago ", " yesterday "});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphView.addSeries(series);
    }
}
