package com.example.luca.stockchartbeta.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface chartWebService {

    // https://sandbox.iexapis.com/stable/stock/{symbol}/chart/max?token={api_token}
    @GET("/stable/stock/{symbol}/chart/max")
    Call<List<chartDataPoint>> getChart(@Path("symbol") String symbol, @Query("token") String api_token);

}
