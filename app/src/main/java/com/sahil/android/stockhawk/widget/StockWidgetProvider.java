package com.sahil.android.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.sahil.android.stockhawk.R;
import com.sahil.android.stockhawk.rest.QuoteCursorAdapter;
import com.sahil.android.stockhawk.data.QuoteColumns;
import com.sahil.android.stockhawk.data.QuoteProvider;
import com.sahil.android.stockhawk.ui.MyStocksActivity;


/**
 * Created by sahil on 5/7/16.
 */

public class StockWidgetProvider extends AppWidgetProvider {

    private QuoteCursorAdapter mCursorAdapter;
    private static final int CURSOR_LOADER_ID = 0;
    public StockWidgetProvider() {
        super();
    }
    Context mcontext;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        mcontext = context;

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void drawView(){

    }



/*
    public void drawView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        mCursorAdapter = new QuoteCursorAdapter(this, null);
        recyclerView.setAdapter(mCursorAdapter);
    }*/

}
