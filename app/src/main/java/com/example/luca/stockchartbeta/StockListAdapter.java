package com.example.luca.stockchartbeta;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StockListAdapter {



    // View holder for the stock item
    public class StockViewHolder extends RecyclerView.ViewHolder {

        TextView companyNameView;
        TextView symbolView;
        TextView exchangeView;

        // constructor
        public StockViewHolder(View itemView) {
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
