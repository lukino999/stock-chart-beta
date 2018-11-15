package com.example.luca.stockchartbeta.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luca.stockchartbeta.R;
import com.example.luca.stockchartbeta.retrofit.IEXChartDataPoint;
import com.example.luca.stockchartbeta.retrofit.IEXClient;
import com.example.luca.stockchartbeta.stockdatabase.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChartFragment extends Fragment {

    private static final String TAG = ChartFragment.class.getSimpleName();
    private Stock mStock;

    // mandatory constructor
    public ChartFragment() {
    }


    public void setStock(Stock stock) {
        mStock = stock;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        TextView textView = rootView.findViewById(R.id.textView);

        if (mStock != null) {
            String symbol = mStock.getSymbol();
            textView.setText("symbol: " + symbol);

            // test
            retrofitTest(symbol);
        }

        return rootView;
    }

    private void retrofitTest(String symbol) {

        // get a Retrofit builder
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.iextrading.com/")
                .addConverterFactory(GsonConverterFactory.create());

        // get retrofit object from builder
        Retrofit retrofit = builder.build();

        // get a client from retrofit
        IEXClient iexClient = retrofit.create(IEXClient.class);

        // create a call from the client
        Call<List<IEXChartDataPoint>> chart = iexClient.chart(symbol);

        // enqueue call
        chart.enqueue(new Callback<List<IEXChartDataPoint>>() {
            @Override
            public void onResponse(Call<List<IEXChartDataPoint>> call, Response<List<IEXChartDataPoint>> response) {
                if (response.isSuccessful()) {
                    List<IEXChartDataPoint> datapoints = response.body();
                    for (IEXChartDataPoint datapoint : datapoints) {
                        Log.d(TAG, "datapoint date: " + datapoint.getDate());
                    }
                } else {
                    Toast.makeText(getContext(), "error code: " + response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<IEXChartDataPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
