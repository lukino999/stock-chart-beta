package com.example.luca.stockchartbeta.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luca.stockchartbeta.R;
import com.example.luca.stockchartbeta.stockdatabase.Stock;

import java.util.List;

public class StockListAdapter extends
        RecyclerView.Adapter<StockListAdapter.StockViewHolder> {

    final private ListItemClickListener mOnClickListener;
    private int mNumberItems = 0;
    private List<Stock> mStockList;

    // StockListAdapter constructor
    // takes ArrayList<Stock> to display and the ListItemClickListener to respond to clicks
    public StockListAdapter(ListItemClickListener itemClickListener) {
        mOnClickListener = itemClickListener;
    }

    public void setStockList(List<Stock> stocks) {
        mStockList = stocks;
        mNumberItems = mStockList.size();
        notifyDataSetChanged();
    }

    public Stock getStock(int i) {
        return mStockList.get(i);
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // reference to layout inflater
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // whether to attach to parent immediately boolean
        boolean shouldAttachToParentImmediately = false;

        // inflate the stock_list_item.xml to a View obj
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


    // onListItemClick will be called when a list item is clicked
    public interface ListItemClickListener {
        void onListItemClick(int index);
    }

    // View holder for the stock item
    class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView companyNameView;
        TextView symbolView;
        TextView exchangeView;

        // StockViewHolder constructor
        // takes a View obj
        StockViewHolder(View itemView) {
            // invoke the superclass
            super(itemView);

            // get references to the textViews
            companyNameView = itemView.findViewById(R.id.tv_company_name);
            symbolView = itemView.findViewById(R.id.tv_symbol);
            exchangeView = itemView.findViewById(R.id.tv_exchange);

            // set the onClickListener to View.OnClickListener
            itemView.setOnClickListener(this);
        }


        // binds the stock data to the views
        void bind(Stock stock) {
            companyNameView.setText(stock.getName());
            symbolView.setText(stock.getSymbol());
            exchangeView.setText(stock.getExchange());
        }

        // View.OnClickListener implementation
        // calls the onListItemClick method from the interface implemented in the calling activity
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


}
