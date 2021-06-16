package com.example.timeismoney.Activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.timeismoney.Activity.adapter.RecyclerAdapter;
import com.example.timeismoney.Activity.model.Coins;
import com.example.timeismoney.Activity.service.Response.DadosService;
import com.example.timeismoney.Activity.service.Response.IService;
import com.example.timeismoney.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;



//Criamos uma interface que é implementada nesta classe, para que possamos estabelecer uma comunicação
//entre a activity e a classe service
public class MainActivity extends AppCompatActivity implements IService {

    private SensorManager sm;
    private Sensor s;
    private SensorEventListener evento;

    private int mov = 0;


    RecyclerView rc;
    LinearLayoutManager llManager;
    ProgressBar progressBar;

    protected void onStart() {
        super.onStart();
        // se tiver usuario logado ele manda direto para tela principal
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Implementação Sensor Acelerometro
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //Acessa os sensores
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //Utilização de sensor de acelerometro
        if (s == null) {
            finish(); //Caso o dispositivo não possua este sensor.
        }

        evento = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] < -4 && mov == 0) {
                    mov++;
                } else {
                    if (event.values[0] > 4 && mov == 1) {
                        mov++;
                    }
                }
                if (mov == 2) {
                    mov = 0;
                    som();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sm.registerListener(evento, s, SensorManager.SENSOR_DELAY_NORMAL);



        /**
         * Criando o recyclerview e setando os ids
         *  Também iniciamos o processo de requisição de dados da API
         *  Criamos o linear layout manager e o atribuímos ao recyclerview
         * **/
        rc = findViewById(R.id.recyclerViewMoedas);
        progressBar = findViewById(R.id.progressBar);
        llManager = new LinearLayoutManager(this);
        rc.setLayoutManager(llManager);
        getCoins();
    }


    public void som(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.notificacao);
        mp.start();
    }


    /**
     * Processo de obtenção de dados
     */
    public void getCoins(){
        DadosService dadosService = new DadosService(this);
        progressBar.setVisibility(View.VISIBLE);
        dadosService.get();
    }

    /**
     *
     *
     * @param response
     * @param action
     *
     * Caso o retorno seja com sucesso, pegamos os dados e o enviamos para o adapter
     * Neste caso específico, o retorno veio numa forma de JsonArray
     */
    @Override
    public void onSuccessServer(@NonNull JsonArray response, int action) {
        Gson gson = new Gson();
        progressBar.setVisibility(View.GONE);
        Coins[] responseCoins = gson.fromJson(response, Coins[].class);

        RecyclerAdapter rcAdapter = new RecyclerAdapter(responseCoins);
        rc.setAdapter(rcAdapter);
    }

    /**
     *
     * @param title
     * @param msg
     *
     * Caso dê algum problema ou erro na chamada
     * TODO
     */
    @Override
    public void onErrorServer(@NonNull String title, @NonNull  String msg) {

    }



}
   /* protected void onStart(){
        super.onStart();
        // se tiver usuario logado ele manda direto para tela principal
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }*/

