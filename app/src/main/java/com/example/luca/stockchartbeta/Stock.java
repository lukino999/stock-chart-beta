package com.example.luca.stockchartbeta;

// This class contains the data relative to the a stock
// Namely:
//      Company name
//      Ticker symbol
//      Exchange code name

public class Stock {

    private String mName;
    private String mSymbol;
    private String mExchange;

    // constructor
    public Stock(String name, String symbol, String exchange) {
        mName = name;
        mSymbol = symbol;
        mExchange = exchange;
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

