package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.ResultSet;

public class Elemento extends AppCompatActivity{

   private String id;
    ConexionBD conexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id = getIntent().getExtras().getString("id");//Aqui recibe el id que carga el qr cuando se crea

        /*try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM elemento WHERE id='"+id+"'");
            rs.next();
            rs.getString("titulo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }*/

        TextView txtCambiado = (TextView)findViewById(R.id.pruebaelemento);
        txtCambiado.setText(id);


    }

}
