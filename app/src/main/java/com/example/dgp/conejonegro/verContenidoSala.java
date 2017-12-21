package com.example.dgp.conejonegro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Alvaro on 03/12/2017.
 */

public class verContenidoSala extends AppCompatActivity{

    private String idZona;
    ConexionBD conexion = null;
    private Button botonListaSalas;
    private Button botonQR;
    private Button botonRutas;
    private Button botonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_sala);
        idZona = getIntent().getExtras().getString("id");

        TextView tvNombreSala = (TextView)findViewById(R.id.NombreSala);
        ImageView foto = (ImageView) findViewById(R.id.foto);

        Sala sala = Museo.getInstance().getSala(idZona);

        tvNombreSala.setText(sala.getNombre());

        URL imageUrl = null;
        try {
            imageUrl = new URL(sala.getImagen());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            Bitmap loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            foto.setImageBitmap(loadedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContenidoSalaFragment salasFragment = (ContenidoSalaFragment) getSupportFragmentManager().findFragmentById(R.id.contenido_sala_container);
        if (salasFragment == null) {
            salasFragment = ContenidoSalaFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.contenido_sala_container, salasFragment).commit();

        }

        crearBotones();

    }

    public void crearBotones(){

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        botonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verContenidoSala.this, verSalas.class));
                finish();
            }
        });

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verContenidoSala.this, Configuracion.class));
                finish();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verContenidoSala.this, lector.class));
                finish();
            }
        });

        botonRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verContenidoSala.this, verRutas.class));
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
