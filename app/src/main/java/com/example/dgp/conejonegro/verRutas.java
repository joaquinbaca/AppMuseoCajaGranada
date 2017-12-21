package com.example.dgp.conejonegro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class verRutas extends AppCompatActivity implements Runnable{
    Museo museo;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rutas);

        progressDialog = ProgressDialog.show(this, "Cargando", "Descargando rutas", true,
                false);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
        String idioma = config.getString("idioma","Español");
        boolean lengSimple = config.getBoolean("lenguajeSimple", false);
        boolean lengSignos = config.getBoolean("lenguajeSignos", false);
        museo = Museo.getInstance(idioma, lengSimple, lengSignos);
        RutasFragment rutasFragment = (RutasFragment) getSupportFragmentManager().findFragmentById(R.id.rutas_container);
        if (rutasFragment == null) {
            rutasFragment = RutasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.rutas_container, rutasFragment).commit();
        }
        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            crearBotones();
            progressDialog.dismiss();
        }
    };

    public void crearBotones(){

    }
}
