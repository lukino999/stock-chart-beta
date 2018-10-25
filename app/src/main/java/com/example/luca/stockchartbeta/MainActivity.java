package com.example.luca.stockchartbeta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup action bar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchButton = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) searchButton.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d(TAG, "onQueryTextSubmit: " + s);

        return true;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        Log.d(TAG, "onQueryTextChange: " + s);

        return true;
    }

    private ArrayList<Stock> getArray() {

        // get inputStream from /res/raw/nasdaq.csv
        InputStream inputStream = getResources().openRawResource(R.raw.nasdaq);

        // get stream reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // iterate through the csv and add to items
        ArrayList<Stock> items = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] value = line.split(",");
                items.add(new Stock(value[1], value[0], "NASDAQ"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
