package com.example.tijoj.stockapitest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tijoj.stockapitest.POJO.StockEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;

public class ChartActivity extends AppCompatActivity {

    public ArrayList<StockEntry> currStockEntry;
    public String company = "MSFT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

//        LineChart lineChart = findViewById(R.id.chart);
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(20,94.56f));
//        entries.add(new Entry(21,92.777f));
//        entries.add(new Entry(22,60.2f));
//        entries.add(new Entry(23,85.54f));
//        entries.add(new Entry(24,84.17f));
//        entries.add(new Entry(25,71.71f));
//        entries.add(new Entry(26,70.71f));
//
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("MON");
//        labels.add("TUE");
//        labels.add("WED");
//        labels.add("THU");
//        labels.add("FRI");
//        labels.add("SAT");
//        labels.add("SUN");
//
//        LineDataSet lineDataSet = new LineDataSet(entries, "Company 1");
//
//        lineDataSet.setColor(Color.RED);
//        lineDataSet.setLineWidth(3);
//        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        lineDataSet.setValueTextSize(15);
//
//        lineDataSet.setFillAlpha(112);
//        lineDataSet.setFillColor(Color.GRAY);
//        lineDataSet.setDrawFilled(true);
//
//
//        List<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(lineDataSet);
//
//        LineData data = new LineData(dataSets);
//        lineChart.setData(data);
//        lineChart.invalidate();
//
//

        getStockPrices("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&apikey=demo");


    }



    public void getStockPrices(String url) {
        final OkHttpClient client = new OkHttpClient();   // 1
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();  // 2


        client.newCall(request).enqueue(new okhttp3.Callback() { // 3
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response)
                    throws IOException {
                final String result = response.body().string();  // 4

                currStockEntry = new ArrayList<>();

                try {
                    JSONObject main = new JSONObject(result);
                    JSONObject time_series = main.getJSONObject("Monthly Time Series");

                    Iterator keys = time_series.keys();

                    int i=0;
                    while (keys.hasNext() && i<13){
                        Object key = keys.next();
                        String date = key.toString();
                        JSONObject prices = time_series.getJSONObject(date);
                        String close = prices.getString("4. close");
                        StockEntry stockEntry = new StockEntry(i, date, close);

                        currStockEntry.add(stockEntry);

                        i++;
                    }

                    populateUI(currStockEntry);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void populateUI(ArrayList<StockEntry> currStockEntry){


        LineChart lineChart = findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();

        for(StockEntry stockEntry : currStockEntry){
            Log.i("STOCK", stockEntry.getDateAsFloat()+" : "+stockEntry.getCloseAsFloat());

            entries.add(new Entry(stockEntry.getOrder(), stockEntry.getCloseAsFloat()));
        }
        Collections.sort(entries, new EntryXComparator());

        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XvalueFormatter(months));
        xAxis.setGranularity(1);



//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("MON");
//        labels.add("TUE");
//        labels.add("WED");
//        labels.add("THU");
//        labels.add("FRI");
//        labels.add("SAT");
//        labels.add("SUN");
//
        LineDataSet lineDataSet = new LineDataSet(entries, company);
//
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setValueTextSize(15);

        lineDataSet.setFillAlpha(112);
        lineDataSet.setFillColor(Color.GRAY);
        lineDataSet.setDrawFilled(true);


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();



    }

    public class XvalueFormatter implements IAxisValueFormatter{

        public String[] months;

        public XvalueFormatter(String[] months) {
            this.months = months;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int intMonth = ((int)value);
            String month = months[intMonth];
            return month;
        }
    }
}
