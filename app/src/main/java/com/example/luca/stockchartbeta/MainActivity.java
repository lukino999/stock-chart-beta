package com.example.luca.stockchartbeta;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        StockListAdapter.ListItemClickListener {

    private static final String TAG = "MainActivity";

    private StockListAdapter mAdapter;
    private RecyclerView mStockList;
    private AppDatabase mDb;
    private List<Stock> fullList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup action bar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        // wire up the recycleView with the adapter
        // get the reference to the recycler view
        mStockList = findViewById(R.id.rv_stock_list);
        // set a LinearLayoutManager to the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStockList.setLayoutManager(layoutManager);
        // improve performance
        mStockList.setHasFixedSize(true);
        // set the adapter
        mAdapter = new StockListAdapter(MainActivity.this);
        mStockList.setAdapter(mAdapter);
        //
        setupViewModel();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate /res/menu/search.xml
        getMenuInflater().inflate(R.menu.search, menu);

        // get a reference to menu item search_button
        MenuItem searchButton = menu.findItem(R.id.search_button);

        // get a reference to the android.support.v7.widget.SearchView
        searchView = (SearchView) searchButton.getActionView();
        // sets the listener to SearchView.OnQueryTextListener
        searchView.setOnQueryTextListener(this);

        // returning true will display the menu
        return true;
    }


    private void setupViewModel() {

        // get a reference to the MainViewModel
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // get inputStream from /res/raw/nasdaq.csv
        InputStream inputStream = getResources().openRawResource(R.raw.nasdaq);
        mainViewModel.setInputStream(inputStream);

        final LiveData<List<Stock>> stocks = mainViewModel.loadAllStocks();
        stocks.observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(@Nullable List<Stock> stocks) {
                Log.d(TAG, "retrieving data from LiveData in ViewModel");
                fullList = stocks;
                mAdapter.setStockList(stocks);
            }
        });
    }

    // SearchView.OnQueryTextListener methods implementation
    @Override
    public boolean onQueryTextSubmit(String s) {

        filterStockList(s);

        hideSoftKeyboard(this);

        // job done
        return true;
    }


    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onQueryTextChange(String s) {

        filterStockList(s);

        // job done
        return true;
    }

    private void filterStockList(String s) {
        // filtered list will contain search results
        List<Stock> filteredList = new ArrayList<Stock>();

        // iterate through the full list and add any stock that contains
        // the search string in either name or symbol
        for (Stock stock : fullList) {
            if (stock.getName().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(stock);
            } else if (stock.getSymbol().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(stock);
            }
        }

        // update RecyclerView
        mAdapter.setStockList(filteredList);
    }


    // StockListAdapter.ListItemClickListener implementation
    @Override
    public void onListItemClick(int index) {
        Log.d(TAG, "onListItemClick: " + index);
    }
}
