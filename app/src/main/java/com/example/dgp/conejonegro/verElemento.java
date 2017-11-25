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

public class verElemento extends AppCompatActivity{

   private String id;
   private Elemento elemento;
    ConexionBD conexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id = getIntent().getExtras().getString("id");//Aqui recibe el id que carga el qr cuando se crea
    /*
        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+id+"'");
            rs.next();
            String nombre = rs.getString("nombre");
            rs = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='"+id+"'");
            rs.next();
            String descripcion = rs.getString("texto");
            elemento = new Elemento(nombre, descripcion);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    */
        elemento = new Elemento("Primer elemento", "Este es el primer elemento");
        TextView txtCambiado = (TextView)findViewById(R.id.elementoNombre);
        txtCambiado.setText(elemento.getNombre());

        txtCambiado = (TextView)findViewById(R.id.elementoTexto);
        txtCambiado.setText(elemento.getDescripcion());

    }

}
