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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class verRutas extends AppCompatActivity implements Runnable{
    Museo museo;
    private ProgressDialog progressDialog;
    private Button botonListaSalas;
    private Button botonQR;
    private Button botonRutas;
    private Button botonConfig;

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

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verRutas.this, Configuracion.class));
                finish();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verRutas.this, lector.class));
                finish();
            }
        });

        botonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verRutas.this, verSalas.class));
                finish();
            }
        });

        traducirInterfaz();
    }

    public void traducirInterfaz(){
        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.principalTexto);
        mTextView.setText(config.getString("principalTexto", "Configuración"));

        TextView mTextView2 = (TextView)findViewById(R.id.principalTextoQR);
        mTextView2.setText(config.getString("principalBotonQR", "Escanear QR"));

        TextView mTextView3 = (TextView)findViewById(R.id.principalTextoRutas);
        mTextView3.setText(config.getString("principalBotonRutas", "Ver Rutas"));

        TextView mTextView4 = (TextView)findViewById(R.id.principalTextoSalas);
        mTextView4.setText(config.getString("principalBotonSalas", "Ver Salas"));

    }
}
