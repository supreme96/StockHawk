package com.sahil.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.sahil.android.stockhawk.R;
import com.sahil.android.stockhawk.data.QuoteColumns;
import com.sahil.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

/**
 * Created by sahil on 7/7/16.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mCursor;
    private Context context = null;
    private int appWidgetId;
    private ArrayList<WidgetQuote> list = new ArrayList<WidgetQuote>();

    @Override
    public void onDestroy() {}

    @Override
    public void onDataSetChanged() {onCreate();}

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onCreate() {

        mCursor = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);

        list.clear();


        for (mCursor.moveToFirst(); ! mCursor.isAfterLast(); mCursor.moveToNext()) {
            list.add(new WidgetQuote(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)),
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)),
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.CHANGE))));
        }
    }

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        /*list.add("sahil");
        list.add("sahil2");
        list.add("sahil3");
        list.add("sahil4");
        list.add("sahil5");
        list.add("sahil6");
        list.add("sahil7");*/

        /*
        mCursor = this.context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);

        list.clear();

        if(mCursor.getCount()!=0){
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()){
            list.add(new WidgetQuote(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)),
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)),
                    mCursor.getString(mCursor.getColumnIndex(QuoteColumns.CHANGE))));
        }}
        else{
            System.out.println("cursor empty sahil");
        }*/

    }

    @Override
    public int getCount() {/*
        if(mCursor.getCount() == 0){
            return 5;
        }
        else{
            return 1;
        }
    }*/
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_item_quote);
        String string = list.get(i).symbol+"\t"+list.get(i).bid+"+\t"+list.get(i).change;
        remoteView.setTextViewText(R.id.stock_symbol, list.get(i).symbol);
        remoteView.setTextViewText(R.id.bid_price, list.get(i).bid+"$");
        remoteView.setTextViewText(R.id.change, list.get(i).change+"$");
        remoteView.setTextColor(R.id.stock_symbol, context.getResources().getColor(R.color.black));
        remoteView.setTextColor(R.id.bid_price, context.getResources().getColor(R.color.black));
        remoteView.setTextColor(R.id.change, context.getResources().getColor(R.color.black));

        //Log.v("widget quote sahil", string);
        //remoteView.setTextViewText(R.id.widget_list_text, "sahil");
        return remoteView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
