package com.example.luca.stockchartbeta;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.InputStream;
import java.util.concurrent.Executors;

@Database(entities = {Stock.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "stock_list";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context, InputStream inputStream) {
        // create new database instance if doesn't exist
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating new database instance");
                sInstance = buildDatabase(context, inputStream);
            }
        }
        Log.d(TAG, "getInstance: Getting database instance");
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context context, final InputStream inputStream) {
        Log.d(TAG, "buildDatabase:");
        return Room.databaseBuilder(context,
                AppDatabase.class, AppDatabase.DATABASE_NAME)
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
                                getInstance(context, inputStream).stockDao().insertAll(Stock.getArray(inputStream));
                            }
                        });
                    }
                })
                .build();
    }

    public abstract StockDao stockDao();


}
