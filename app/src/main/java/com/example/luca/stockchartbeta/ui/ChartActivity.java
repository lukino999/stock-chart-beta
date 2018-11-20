package com.example.luca.stockchartbeta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.luca.stockchartbeta.R;
import com.example.luca.stockchartbeta.stockdatabase.Stock;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = ChartActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(getResources().getString(R.string.extra_stock))) {

            // get a chart fragment
            ChartFragment chartFragment = new ChartFragment();

            // set Stock

            Stock stock = (Stock) callerIntent.getExtras().getSerializable(getResources().getString(R.string.extra_stock));
            chartFragment.setStock(stock);

            // call the fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.chart_container, chartFragment)
                    .commit();

        }

    }
}
