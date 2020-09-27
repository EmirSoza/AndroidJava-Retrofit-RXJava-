package com.emirhan.retrofitcurrency.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirhan.retrofitcurrency.R;
import com.emirhan.retrofitcurrency.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<CryptoModel> cryptoModelArrayList;
    private String[] colors = {"#93bca8","#bc93a7","#b8d6fd","#b2fbbb","#ffe8e0","#eae4d3","#987dc5"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_loayout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {
        holder.bind(cryptoModelArrayList.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return cryptoModelArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView textCurrency;
        TextView textPrice;


        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(CryptoModel cryptoModel, String[] colors, int position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 7]));
            textCurrency = itemView.findViewById(R.id.currencyText);
            textPrice = itemView.findViewById(R.id.priceText);
            textCurrency.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }
    }

}
