package com.example.luca.stockchartbeta;

// This class contains the data relative to the a stock
// Namely:
//      Company name
//      Ticker symbol
//      Exchange code name

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Entity(tableName = "stock")
@TypeConverters(StockTypeConverter.class)
public class Stock {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String symbol;
    private String exchange;

    // constructors
    @Ignore
    public Stock(String name, String symbol, String exchange) {
        this.name = name;
        this.symbol = symbol;
        this.exchange = exchange;
    }

    public Stock(int id, String name, String symbol, String exchange) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.exchange = exchange;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public static ArrayList<Stock> getArray(InputStream inputStream) {

        // get inputStream from /res/raw/nasdaq.csv
        // InputStream inputStream = getResources().openRawResource(R.raw.nasdaq);

        // get stream reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // iterate through the csv and add to items
        ArrayList<Stock> items = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] value = line.split(",");
                items.add(new Stock(value[1], value[0], "NASDAQ"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }


}

