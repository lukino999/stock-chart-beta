package com.example.luca.stockchartbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChartFragment extends Fragment {

    private Stock mStock;

    // mandatory constructor
    public ChartFragment() {
    }

    public void setStock(Stock stock) {
        mStock = stock;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        TextView textView = rootView.findViewById(R.id.textView);

        if (mStock != null) {
            textView.setText("symbol: " + mStock.getSymbol());
        }



        return rootView;

    }
}
