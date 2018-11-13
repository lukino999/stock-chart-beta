package com.example.luca.stockchartbeta;

import android.content.Intent;
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
            int id = callerIntent.getExtras().getInt(MainActivity.STOCK_ID);
            Log.d(TAG, "onCreate: " + id);
        }

    }
}
