package com.example.luca.stockchartbeta.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface chartWebService {

    @GET("/1.0/stock/{symbol}/chart/ytd")
    Call<List<chartDataPoint>> getChart(@Path("symbol") String symbol);

}
