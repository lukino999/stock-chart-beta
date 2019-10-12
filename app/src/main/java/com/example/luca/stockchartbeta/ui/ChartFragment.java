package com.example.luca.stockchartbeta.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.luca.stockchartbeta.R;
import com.example.luca.stockchartbeta.retrofit.chartDataPoint;
import com.example.luca.stockchartbeta.retrofit.chartWebService;
import com.example.luca.stockchartbeta.stockdatabase.Stock;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChartFragment extends Fragment {

    private static final String TAG = ChartFragment.class.getSimpleName();
    private Stock mStock;
    private CandleStickChart chart;

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

        chart = rootView.findViewById(R.id.chart_container);

        if (mStock != null) {
            // test
            retrofitTest(mStock.getSymbol(), chart);
        }

        return rootView;
    }

    private void retrofitTest(String symbol, final CandleStickChart chart) {

        // get a Retrofit builder
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create());

        // get retrofit object from builder
        Retrofit retrofit = builder.build();

        // get a client from retrofit
        chartWebService chartWebService = retrofit.create(chartWebService.class);

        // create a call from the client
        final String API_TOKEN = getString(R.string.API_TOKEN);
        Call<List<chartDataPoint>> chartData = chartWebService.getChart(symbol, API_TOKEN);

        // enqueue call
        chartData.enqueue(new Callback<List<chartDataPoint>>() {
            @Override
            public void onResponse(Call<List<chartDataPoint>> call, Response<List<chartDataPoint>> response) {
                if (response.isSuccessful()) {
                    List<chartDataPoint> datapoints = response.body();
                    for (chartDataPoint datapoint : datapoints) {
                        Log.d(TAG, "datapoint date: " + datapoint.getDate());
                    }
                    getChart(datapoints, chart, mStock);
                } else {
                    Toast.makeText(getContext(), "error code: " + response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<chartDataPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getChart(List<chartDataPoint> datapoints, CandleStickChart candleStickChart, Stock stock) {

        // list of candles
        List<CandleEntry> entries = new ArrayList<>();
        // list of labels (dates)
        List<String> labels = new ArrayList<>();

        // populate list
        for (int i = 0; i < datapoints.size(); i++) {

            chartDataPoint dataPoint = datapoints.get(i);

            // add candle
            entries.add(new CandleEntry(Float.valueOf(i),
                    dataPoint.getHigh(),
                    dataPoint.getLow(),
                    dataPoint.getOpen(),
                    dataPoint.getClose()));

            // add label
            labels.add(dataPoint.getDate());

        }

        // create dataset
        CandleDataSet dataSet = new CandleDataSet(entries, stock.getSymbol());

        dataSet.setIncreasingColor(Color.GREEN);
        dataSet.setDecreasingColor(Color.RED);
        dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        dataSet.setShadowColor(Color.LTGRAY);
        dataSet.setBarSpace(.25f);
        dataSet.setDrawValues(false);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setNeutralColor(Color.LTGRAY);
        // remove the 2 colored squares from legend
        dataSet.setForm(Legend.LegendForm.NONE);

        // set x axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);


        CandleData candleData = new CandleData(dataSet);

        Description description = new Description();
        description.setText("");
        candleStickChart.setDescription(description);

        // show chart
        candleStickChart.setData(candleData);
        candleStickChart.invalidate();

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues.get((int) value);
        }

        // remove??
        /** this is only needed if numbers are returned, else return 0 */
        //public int getDecimalDigits() { return 0; }
    }


}


