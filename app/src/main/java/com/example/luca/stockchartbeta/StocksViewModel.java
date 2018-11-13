package com.example.luca.stockchartbeta;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.InputStream;
import java.util.List;

public class StocksViewModel extends AndroidViewModel {

    private static final String TAG = StocksViewModel.class.getSimpleName();

    private LiveData<List<Stock>> mStocks;


    private InputStream mInputStream;

    public StocksViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "StocksViewModel: retrieving data from database");
        StocksDatabase database = StocksDatabase.getInstance(this.getApplication(), mInputStream);
        mStocks = database.stockDao().loadAllStocks();
    }


    public void setInputStream(InputStream mInputStream) {
        this.mInputStream = mInputStream;
    }

    public LiveData<List<Stock>> loadAllStocks() {
        return mStocks;
    }


}
