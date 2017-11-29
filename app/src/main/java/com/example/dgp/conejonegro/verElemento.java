package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;

public class verElemento extends AppCompatActivity{

   private String id;
   private Elemento elemento;
    ConexionBD conexion = null;
    private String nombre1 ="0";
    private String descripcion1="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id = getIntent().getExtras().getString("id");//Aqui recibe el id que carga el qr cuando se crea
        Button btn = (Button)findViewById(R.id.Binicio);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, Principal.class));
                finish();
            }
        });
        Button btn1 = (Button)findViewById(R.id.Bescaner);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, lector.class));
                finish();
            }
        });

        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+id+"'");
            rs.next();
            String nombre = rs.getString("nombre");
            nombre1=nombre;
            rs = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='"+id+"'");
            rs.next();
            String descripcion = rs.getString("texto");
            descripcion1=descripcion;
            elemento = new Elemento(nombre, descripcion);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        TextView txtCambiado = (TextView)findViewById(R.id.elementoNombre);
        txtCambiado.setText(nombre1);

        txtCambiado = (TextView)findViewById(R.id.elementoTexto);
        txtCambiado.setText(descripcion1);


    }

}
