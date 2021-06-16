package com.example.timeismoney.Activity.service.Response;

import androidx.annotation.NonNull;

import com.example.timeismoney.Activity.service.ApiEndpoints;
import com.example.timeismoney.Activity.service.Retrofit;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosService {

    private final IService view;
    private final ApiEndpoints apiEndpoints;
    private Call<JsonArray> call;

    public DadosService(@NonNull IService view) {
        apiEndpoints = new Retrofit().start("https://api.coingecko.com/api/v3/").create(ApiEndpoints.class);
        this.view = view;
    }

    public void get() {
        call = apiEndpoints.getItens();

        call.enqueue(new Callback<JsonArray>() {


            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                //Null check
                assert response.body() != null;
                view.onSuccessServer(response.body(), 200);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                view.onErrorServer("Erro", "");
            }


        });
    }
}
