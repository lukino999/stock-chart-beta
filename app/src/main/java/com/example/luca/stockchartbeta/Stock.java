package com.example.luca.stockchartbeta;

// This class contains the data relative to the a stock
// Namely:
//      Company name
//      Ticker symbol
//      Exchange code name

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stock")
public class Stock {

    @PrimaryKey(autoGenerate = true)
    private int mID;
    private String mName;
    private String mSymbol;
    private String mExchange;

    // constructors
    @Ignore
    public Stock(String name, String symbol, String exchange) {
        mName = name;
        mSymbol = symbol;
        mExchange = exchange;
    }

    public Stock(int id, String name, String symbol, String exchange) {
        mID = id;
        mName = name;
        mSymbol = symbol;
        mExchange = exchange;
    }

    public int getId() {
        return mID;
    }

    public String getCompanyName() {
        return mName;
    }

    public String getTickerSymbol() {
        return mSymbol;
    }

    public String getExchange() {
        return mExchange;
    }


}

