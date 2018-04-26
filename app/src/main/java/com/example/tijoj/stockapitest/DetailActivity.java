package com.example.tijoj.stockapitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tijoj.stockapitest.conversion.JsonToProduct;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class DetailActivity extends AppCompatActivity {

    public TextView symbol, compName, indus, website, desc, ceo, sect;
    public ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String base_url = "https://api.iextrading.com/1.0/stock/";

        String comp = getIntent().getStringExtra("TICKER");

        String main_url = base_url+comp;

        String detail_url = main_url+"/company";
        String logo_url = main_url+"/logo";

        symbol = findViewById(R.id.tv_symbol);
        compName = findViewById(R.id.tv_company_name);
        indus = findViewById(R.id.tv_industry);
        website = findViewById(R.id.tv_website);
        desc = findViewById(R.id.tv_description);
        ceo = findViewById(R.id.tv_ceo);
        sect = findViewById(R.id.tv_sector);

        logoImageView = findViewById(R.id.iv_logo);

        getProducts(detail_url);

        getLogo(logo_url);

    }

    public void getProducts(String url) {
        OkHttpClient client = new OkHttpClient();   // 1
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
                final Product product = JsonToProduct.getProductArrayListFromJson(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        symbol.setText(product.getSymbol());
                        compName.setText(product.getCompanyName());
                        indus.setText(product.getIndustry());
                        website.setText(product.getWebsite());
                        desc.setText(product.getDescription());
                        ceo.setText(product.getCEO());
                        sect.setText(product.getSector());
                    }
                });

            }
        });
    }

    public void getLogo(String url) {
        OkHttpClient client = new OkHttpClient();   // 1
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();  // 2


        client.newCall(request).enqueue(new okhttp3.Callback() { // 3
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response)
                    throws IOException {
                final String result = response.body().string();  //
                Log.i("LOGO", result);
                String pic_url = "";
                try {
                    JSONObject reader = new JSONObject(result);
                    pic_url = reader.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                final String finalPic_url = pic_url;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.get().load(finalPic_url).into(logoImageView);
                    }
                });

            }
        });
    }
}
