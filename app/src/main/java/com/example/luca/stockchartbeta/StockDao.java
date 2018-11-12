package com.example.luca.stockchartbeta;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock ORDER BY name")
    LiveData<List<Stock>> loadAllStocks();

    @Insert
    void insertStock(Stock stock);

    @Insert
    void insertAll(List<Stock> Stocks);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStock(Stock stock);

}

