package com.example.luca.stockchartbeta;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock ORDER BY mName")
    ArrayList<Stock> loadAllStocks();

    @Insert
    void insertStock(Stock stock);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStock(Stock stock);

}
