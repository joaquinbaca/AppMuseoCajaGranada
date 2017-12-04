package com.example.dgp.conejonegro;

import android.content.Intent;
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

    }
}
