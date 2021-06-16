package com.example.timeismoney.Activity.service;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ApiEndpoints {

    @NonNull
    @Headers( {"Content-type: application/json; charset=UTF-8"} )
    @GET("coins/markets?vs_currency=brl&order=market_cap_desc&per_page=15&page=1&sparkline=false")
    Call<JsonArray> getItens();
}
