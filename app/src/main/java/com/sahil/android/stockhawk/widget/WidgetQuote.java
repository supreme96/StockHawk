package com.sahil.android.stockhawk.widget;

/**
 * Created by sahil on 9/7/16.
 */

public class WidgetQuote {

    public String symbol;
    public String bid;
    public String change;

    public WidgetQuote(String symbol, String bid, String change) {
        this.symbol = symbol;
        this.bid = bid;
        this.change = change;
    }

}
