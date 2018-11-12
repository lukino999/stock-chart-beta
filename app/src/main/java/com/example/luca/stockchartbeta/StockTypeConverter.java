package com.example.luca.stockchartbeta;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StockTypeConverter {
    @TypeConverter
    public static List<Stock> stringToStock(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Stock>>() {}.getType();
        List<Stock> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String stockToString(List<Stock> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Stock>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
