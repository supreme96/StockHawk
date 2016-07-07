package com.sahil.android.stockhawk.widget;

import android.app.LauncherActivity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sahil.android.stockhawk.R;
import com.sahil.android.stockhawk.data.QuoteColumns;
import com.sahil.android.stockhawk.data.QuoteProvider;
import com.sahil.android.stockhawk.rest.QuoteCursorAdapter;

import java.util.ArrayList;

import static android.R.id.input;

/**
 * Created by sahil on 7/7/16.
 */

public class ListProvider extends ListActivity implements RemoteViewsService.RemoteViewsFactory, LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Cursor mCursor;
    private QuoteCursorAdapter mCursorAdapter;
    private ArrayList stocksList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    @Override
    public void onCreate() {

        //mCursorAdapter = new QuoteCursorAdapter(context, mCursor);
    }

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        getLoaderManager().initLoader(0, null, this);
        mCursor = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_list_item);
        mCursor.moveToPosition(i);
        //remoteView.setTextViewText(R.id.stock_symbol, mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)));
        //remoteView.setTextViewText(R.id.bid_price, mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)));
        //remoteView.setTextViewText(R.id.change, mCursor.getString(mCursor.getColumnIndex(QuoteColumns.CHANGE)));
        Log.v("widget debug", mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL))+"\t"+mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE))+"  "+getString(mCursor.getColumnIndex(QuoteColumns.CHANGE)));
        remoteView.setTextViewText(R.id.widget_list_text, mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL))+"\t"+mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE))+"  "+getString(mCursor.getColumnIndex(QuoteColumns.CHANGE)));
        return remoteView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        // This narrows the return to only the stocks that are most current.
        return new CursorLoader(context, QuoteProvider.Quotes.CONTENT_URI,
                new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.CHANGE},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        mCursorAdapter.swapCursor(data);
        mCursor = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mCursorAdapter.swapCursor(null);
    }
}
