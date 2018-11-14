package com.example.luca.stockchartbeta;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.luca.stockchartbeta.stockdatabase.Stock;
import com.example.luca.stockchartbeta.stockdatabase.StocksDatabase;

import java.util.List;

public class StocksViewModel extends AndroidViewModel {

    private static final String TAG = StocksViewModel.class.getSimpleName();

    private LiveData<List<Stock>> mStocks;


    public StocksViewModel(@NonNull Application application) {
        super(application);


        Log.d(TAG, "StocksViewModel: retrieving data from database");
        StocksDatabase database = StocksDatabase.getInstance(this.getApplication());
        mStocks = database.stockDao().loadAllStocks();
    }

    public LiveData<List<Stock>> loadAllStocks() {
        return mStocks;
    }


}
