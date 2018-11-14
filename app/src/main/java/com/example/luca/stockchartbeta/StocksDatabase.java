package com.example.luca.stockchartbeta;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

@Database(entities = {Stock.class}, version = 1, exportSchema = false)
public abstract class StocksDatabase extends RoomDatabase {

    private static final String TAG = StocksDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "stock_list";
    private static StocksDatabase sInstance;

    public static StocksDatabase getInstance(Context context) {

        // create new database instance if doesn't exist
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating new database instance");
                sInstance = buildDatabase(context);
            }
        }
        Log.d(TAG, "getInstance: Getting database instance");
        return sInstance;
    }

    private static StocksDatabase buildDatabase(final Context context) {
        Log.d(TAG, "buildDatabase:");
        return Room.databaseBuilder(context,
                StocksDatabase.class, StocksDatabase.DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    // this will be called only at first run, when the database hasn't been created
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d(TAG, "onCreate: Creating database file");
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run: stockDao().insertAll(Stocks)");
                                getInstance(context).stockDao().insertAll(Stock.getArray(context));
                            }
                        });
                    }
                })
                .build();
    }

    public abstract StockDao stockDao();


}
