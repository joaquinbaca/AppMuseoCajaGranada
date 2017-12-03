package com.example.dgp.conejonegro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        TextView tvDescripcionSala = (TextView)findViewById(R.id.DescripcionSala);


        Sala sala = Museo.getInstance().getSala(idZona);

        tvNombreSala.setText(sala.getNombre());
        tvDescripcionSala.setText(sala.getDescripcion());
    }
}
