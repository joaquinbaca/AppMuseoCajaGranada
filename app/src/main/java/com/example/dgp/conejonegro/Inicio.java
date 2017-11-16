package com.example.dgp.conejonegro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;


public class Inicio extends AppCompatActivity {

    Button button;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        traducirInterfaz();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit = (EditText) findViewById(R.id.inicioEditText);
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

    public void traducirInterfaz(){
        SharedPreferences config=getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.inicioTexto);
        mTextView.setText(config.getString("InicioInicioTexto", "Bienvenidos al museo Caja Granada, pregunta por el código de la aplicación en recepción para poder acceder."));

        mEdit = (EditText) findViewById(R.id.inicioEditText);
        mEdit.setHint(config.getString("InicioInicioEditText", "Escribe el código"));

        button = (Button)findViewById(R.id.inicioBotonEnviar);
        button.setText(config.getString("InicioInicioBotonEnviar", "ENVIAR"));
    }



}
