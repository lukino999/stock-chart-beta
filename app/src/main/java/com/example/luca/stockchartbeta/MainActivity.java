package com.example.luca.stockchartbeta;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
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
    private static final String SEARCH_QUERY = "SEARCH_QUERY";
    private StockListAdapter mAdapter;
    private RecyclerView mStockList;
    private List<Stock> mFullList;
    private SearchView mSearchView;
    private MenuItem mSearchButton;
    private CharSequence mSearchQuery = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve the query
        if (savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getCharSequence(SEARCH_QUERY);
        }

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
    protected void onSaveInstanceState(Bundle outState) {
        // save the query
        outState.putCharSequence(SEARCH_QUERY, mSearchQuery);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate /res/menu/search.xml
        getMenuInflater().inflate(R.menu.search, menu);

        // get a reference to menu item search_button
        mSearchButton = menu.findItem(R.id.search_button);


        // get a reference to the android.support.v7.widget.SearchView
        mSearchView = (SearchView) mSearchButton.getActionView();
        // sets the listener to SearchView.OnQueryTextListener
        mSearchView.setOnQueryTextListener(this);

        // returning true will display the menu
        return true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // since configuration change is handled manually
        // call setupViewModel()
        setupViewModel();
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
                mFullList = stocks;
                mAdapter.setStockList(stocks);

                // restore query to before configChange
                Log.d(TAG, "onChanged: mSearchQuery -> " + mSearchQuery);
                if (mSearchQuery != "") {
                    mSearchButton.expandActionView();
                    mSearchView.setQuery(mSearchQuery, true);
                }
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


    // hide soft keyboard
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public boolean onQueryTextChange(String s) {

        // update mSearchQuery. It will be retrieved after config change
        mSearchQuery = s;

        // filter list
        filterStockList(s);

        // job done
        return true;
    }


    private void filterStockList(String s) {
        // filtered list will contain search results
        List<Stock> filteredList = new ArrayList<>();

        // iterate through the full list and add any stock that contains
        // the search string in either name or symbol
        for (Stock stock : mFullList) {
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
