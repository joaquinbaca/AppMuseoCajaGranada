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

/**
 * Created by Alvaro on 03/12/2017.
 */

public class verContenidoSala extends AppCompatActivity{

    private String idZona;
    private Elemento elemento;
    ConexionBD conexion = null;
    private String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_sala);
        idZona = getIntent().getExtras().getString("id");

        TextView tvNombreSala = (TextView)findViewById(R.id.NombreSala);
        TextView tvDescripcionSala = (TextView)findViewById(R.id.DescripcionSala);

        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM ZONA WHERE idZona='"+idZona+"'");
            rs.next();
            String nombreSala = rs.getString("nombre");
            rs = conexion.hacerConsulta("SELECT * FROM `DESCRIPCION-ZONA` WHERE idZona='"+idZona+"'");
            rs.next();
            String descripcionSala = rs.getString("texto");

            tvNombreSala.setText(nombreSala);
            tvDescripcionSala.setText(descripcionSala);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
}
