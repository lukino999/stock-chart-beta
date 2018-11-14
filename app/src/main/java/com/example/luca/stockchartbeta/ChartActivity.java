package com.example.luca.stockchartbeta;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChartActivity extends AppCompatActivity {

    private static final String TAG = ChartActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(MainActivity.STOCK)) {

            // get a chart fragment
            ChartFragment chartFragment = new ChartFragment();

            // set Stock

            Stock stock = (Stock) callerIntent.getExtras().getSerializable(MainActivity.STOCK);
            chartFragment.setStock(stock);

            // call the fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.chart_container, chartFragment)
                    .commit();

        }

    }
}
