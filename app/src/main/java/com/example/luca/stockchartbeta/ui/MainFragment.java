package com.example.luca.stockchartbeta.ui;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.luca.stockchartbeta.R;
import com.example.luca.stockchartbeta.StocksViewModel;
import com.example.luca.stockchartbeta.stockdatabase.Stock;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener,
        StockListAdapter.ListItemClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String SEARCH_QUERY = "SEARCH_QUERY";
    private StockListAdapter mAdapter;
    private RecyclerView mStockList;
    private List<Stock> mFullList;
    private SearchView mSearchView;
    private MenuItem mSearchButton;
    private CharSequence mSearchQuery = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // retrieve the query
        if (savedInstanceState != null) {
            mSearchQuery = savedInstanceState.getCharSequence(SEARCH_QUERY);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // setup action bar
        Toolbar toolbar = root.findViewById(R.id.my_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // wire up the recycleView with the adapter
        // get the reference to the recycler view
        mStockList = root.findViewById(R.id.rv_stock_list);
        // set a LinearLayoutManager to the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mStockList.setLayoutManager(layoutManager);
        // improve performance
        mStockList.setHasFixedSize(true);
        // set the adapter
        mAdapter = new StockListAdapter(MainFragment.this);
        mStockList.setAdapter(mAdapter);
        //
        setupViewModel();

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the query
        outState.putCharSequence(SEARCH_QUERY, mSearchQuery);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflate /res/menu/search.xml
        inflater.inflate(R.menu.search, menu);

        // get a reference to menu item search_button
        mSearchButton = menu.findItem(R.id.search_button);


        // get a reference to the android.support.v7.widget.SearchView
        mSearchView = (SearchView) mSearchButton.getActionView();
        // sets the listener to SearchView.OnQueryTextListener
        mSearchView.setOnQueryTextListener(this);

    }

    private void setupViewModel() {

        // get a reference to the StocksViewModel
        StocksViewModel stocksViewModel = ViewModelProviders.of(this)
                .get(StocksViewModel.class);

        // get the stock list
        final LiveData<List<Stock>> stocks = stocksViewModel.loadAllStocks();
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

        hideSoftKeyboard(getActivity());

        // job done
        return true;
    }

    // hide soft keyboard
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                0);
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
        Log.d(TAG, "onListItemClick: " + mAdapter.getStock(index).getSymbol());
        Intent showChart = new Intent(getActivity(), ChartActivity.class);
        // send stock id through the intent
        showChart.putExtra(getString(R.string.extra_stock), mAdapter.getStock(index));
        startActivity(showChart);
    }


}
