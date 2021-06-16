package com.example.timeismoney.Activity.service;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    @NonNull
    public retrofit2.Retrofit start(@NonNull String url) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build();

        retrofit2.Retrofit.Builder builder = new retrofit2.Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
