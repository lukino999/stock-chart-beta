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


}

