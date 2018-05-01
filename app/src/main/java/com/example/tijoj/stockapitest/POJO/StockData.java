package com.example.tijoj.stockapitest.POJO;

import com.example.tijoj.stockapitest.Helpers.PojoConverters;

/**
 * Created by tijoj on 4/27/2018.
 *
 * ORDER IS THE ORDER IN WHICH THE STOCK WAS IT WAS RETRIEVED
 * DATE IS THE DATE WHEN THE STOCK PRICE WAS RETRIEVED
 * CLOSE IS THE CLOSING PRICE ON THAT DAY
 *
 */

public class StockData {

    public int order;
    public String date;
    public String close;

    public StockData(int order, String date, String close) {
        this.date = date;
        this.close = close;
        this.order = order;
    }

    public float getDateAsFloat() {
        return PojoConverters.getYearAsFloat(date);
    }

    public float getCloseAsFloat() {
        float value = Float.valueOf(close);
        return value;
    }

    public String getDateAsYear() {
        return PojoConverters.getMonthFromDate(date);
    }

    public String getCloseAsDollor() {
        return PojoConverters.getDollarAmount(close);
    }

    public int getOrder() {
        return order;
    }
}