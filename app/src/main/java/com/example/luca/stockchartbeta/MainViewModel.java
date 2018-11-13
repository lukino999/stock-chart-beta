package com.example.luca.stockchartbeta;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.InputStream;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private LiveData<List<Stock>> mStocks;


    private InputStream mInputStream;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "MainViewModel: retrieving data from database");
        AppDatabase database = AppDatabase.getInstance(this.getApplication(), mInputStream);
        mStocks = database.stockDao().loadAllStocks();
    }


    public void setInputStream(InputStream mInputStream) {
        this.mInputStream = mInputStream;
    }

    public LiveData<List<Stock>> loadAllStocks() {
        return mStocks;
    }


}
