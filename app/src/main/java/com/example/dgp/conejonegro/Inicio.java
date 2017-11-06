package com.example.dgp.conejonegro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Inicio extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);





        Button btn = (Button) findViewById(R.id.Enviar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit = (EditText) findViewById(R.id.codigo);
                String contenido = mEdit.getText().toString();
                String clave="";

                //Crea objeto conexion a BD
                ConexionBD conexion = null;

                //Con el constructor se conecta automaticamente a la BD
                try {
                    conexion = new ConexionBD();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }

                //Consulta
                try {
                    ResultSet rs = conexion.hacerConsulta("SELECT * FROM clave");
                    rs.next();
                    clave = rs.getString("clave");
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                if (contenido.equals(clave)) {
                    SharedPreferences config = getSharedPreferences("config", Context.MODE_PRIVATE);
                    if (config.contains("idioma")) {
                        Usuario.initInstance(config.getString("idioma", "Español"), config.getBoolean("subtitulos", false), config.getBoolean("lenguajeSimple", false),
                                config.getBoolean("lenguajeSignos", false), config.getBoolean("sonido", false));
                        startActivity(new Intent(Inicio.this, Principal.class));
                    } else
                        startActivity(new Intent(Inicio.this, Configuracion.class));
                } else
                    startActivity(new Intent(Inicio.this, Error.class));
                try {
                    conexion.cerrarBasedeDatos();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }



}
