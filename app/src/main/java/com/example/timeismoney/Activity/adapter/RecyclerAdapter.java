package com.example.timeismoney.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeismoney.Activity.activity.MainActivity;
import com.example.timeismoney.Activity.model.Coins;
import com.example.timeismoney.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private final List<Coins> listCoinsRecycler;



    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_moedas, parent, false);
        Context context = parent.getContext();
        return new RecyclerViewHolder(view);
    }

    public RecyclerAdapter(Coins[] responseCoins) {

        this.listCoinsRecycler = new ArrayList<Coins>(Arrays.asList(responseCoins));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        final Coins coin = listCoinsRecycler.get(position);

        holder.tv1.setText(coin.name);
        holder.tv2.setText(coin.symbol);
        holder.tv3.setText(coin.current_price);
        Picasso.get().load(coin.image).into(holder.im1);

        if (coin.current_price.equals("")) {
            holder.tv3.setTextColor(Color.RED);
            holder.tv3.setText("Não encontrado");
        }

    }

    @Override
    public int getItemCount() {
        return listCoinsRecycler.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView im1;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.textView1);
            tv2 = itemView.findViewById(R.id.textView2);
            tv3 = itemView.findViewById(R.id.textView3);
            im1 = itemView.findViewById(R.id.ig1);

        }

    }





}
