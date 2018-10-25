package com.example.luca.stockchartbeta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StockListAdapter extends
        RecyclerView.Adapter<StockListAdapter.StockViewHolder> {

    private int mNumberItems;
    private List<Stock> mStockList;


    public StockListAdapter(List<Stock> stockList) {
        mStockList.addAll(stockList);
        mNumberItems = stockList.size();
    }


    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.stock_list_item, viewGroup, shouldAttachToParentImmediately);

        return new StockViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int i) {
        stockViewHolder.bind(mStockList.get(i));
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    // View holder for the stock item
    class StockViewHolder extends RecyclerView.ViewHolder {

        TextView companyNameView;
        TextView symbolView;
        TextView exchangeView;

        // constructor
        StockViewHolder(View itemView) {
            super(itemView);

            companyNameView = itemView.findViewById(R.id.tv_company_name);
            symbolView = itemView.findViewById(R.id.tv_symbol);
            exchangeView = itemView.findViewById(R.id.tv_exchange);
        }


        // binds the stock data to the views
        void bind(Stock stock) {
            companyNameView.setText(stock.getCompanyName());
            symbolView.setText(stock.getTickerSymbol());
            exchangeView.setText(stock.getExchange());
        }

    }


}
