package com.example.luca.stockchartbeta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        StockListAdapter.ListItemClickListener {

    private static final String TAG = "MainActivity";

    private StockListAdapter mAdapter;
    private RecyclerView mStockList;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup action bar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // get inputStream from /res/raw/nasdaq.csv
        InputStream inputStream = getResources().openRawResource(R.raw.nasdaq);
        mDb = AppDatabase.getInstance(getApplicationContext(), inputStream);

        // wire up the recycleView with the adapter
        // get the reference to the recycler view
        mStockList = findViewById(R.id.rv_stock_list);
        // set a LinearLayoutManager to the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockList.setLayoutManager(layoutManager);
        // improve performance
        mStockList.setHasFixedSize(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate /res/menu/search.xml
        getMenuInflater().inflate(R.menu.search, menu);

        // get a reference to menu item search_button
        MenuItem searchButton = menu.findItem(R.id.search_button);

        // get a reference to the android.support.v7.widget.SearchView
        SearchView searchView = (SearchView) searchButton.getActionView();
        // sets the listener to SearchView.OnQueryTextListener
        searchView.setOnQueryTextListener(this);

        // returning true will display the menu
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                final List<Stock> temp = mDb.stockDao().loadAllStocks();

                mAdapter = new StockListAdapter(temp, MainActivity.this);

                mStockList.setAdapter(mAdapter);

            }
        });



    }

    // SearchView.OnQueryTextListener methods implementation
    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d(TAG, "onQueryTextSubmit: " + s);

        // job done
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d(TAG, "onQueryTextChange: " + s);

        // job done
        return true;
    }


    // StockListAdapter.ListItemClickListener implementation
    @Override
    public void onListItemClick(int index) {
        Log.d(TAG, "onListItemClick: " + index);
    }
}
