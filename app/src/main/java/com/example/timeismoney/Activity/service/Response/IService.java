package com.example.timeismoney.Activity.service.Response;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;


public interface IService {
    void onSuccessServer(@NonNull JsonArray response, int action);

    void onErrorServer(@NonNull String title, @NonNull String msg);

}

