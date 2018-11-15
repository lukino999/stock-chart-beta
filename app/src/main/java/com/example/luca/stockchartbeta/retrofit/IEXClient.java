package com.example.luca.stockchartbeta.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IEXClient {

    // FOR TESTING ONLY: retrieve only 5 data points harcoded
    @GET("/1.0/stock/{symbol}/chart?chartLast=5")
    Call<List<IEXChartDataPoint>> chart(@Path("symbol") String symbol);
}
