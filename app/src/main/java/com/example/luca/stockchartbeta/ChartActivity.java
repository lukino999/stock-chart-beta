package com.example.luca.stockchartbeta;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = "ChartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(MainActivity.STOCK_ID)) {

            // get a chart fragment
            ChartFragment chartFragment = new ChartFragment();

            // set Stock id
            int id = callerIntent.getExtras().getInt(MainActivity.STOCK_ID);
            chartFragment.setStockId(id);

            // call the fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.chart_container, chartFragment)
                    .commit();

        }

    }
}
