package com.example.tijoj.stockapitest.conversion;

import com.example.tijoj.stockapitest.POJO.Product;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by tijoj on 4/25/2018.
 */

public class JsonToProduct {



    public static Product getProductArrayListFromJson(String json){

        ArrayList<Product> productArrayList= new ArrayList<>();

        Gson gson = new Gson();

        Product product = gson.fromJson(json, Product.class);

        return product;
    }
}
