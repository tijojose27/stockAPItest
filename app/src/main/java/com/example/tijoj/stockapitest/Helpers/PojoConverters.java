package com.example.tijoj.stockapitest.Helpers;

import com.example.tijoj.stockapitest.POJO.StockData;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tijoj on 4/27/2018.
 */

public class PojoConverters {

    public static String getMonthFromDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = "";
        try {
            Date date1 = sdf.parse(date);
            sdf.applyPattern("MMM yy");
            currDate = sdf.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currDate;
    }


    public static String getDollarAmount(String amount){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        float stuff = Float.valueOf(amount);
        String currAmount = format.format(stuff);
        return currAmount;
    }

    public static float getYearAsFloat(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = "";
        try {
            Date date1 = sdf.parse(date);
            sdf.applyPattern("MM");
            currDate = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Float.parseFloat(currDate);
    }


    public static String[] getMonthsFromData(ArrayList<StockData> data){
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan"};

        String[] orderedMonths = new String[13];

        for(int i =0; i<data.size();i++){
            orderedMonths[i] = months[((int) data.get(i).getDateAsFloat())-1];
        }


        return orderedMonths;
    }



}
