package com.example.dgp.conejonegro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.StrictMode;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Inicio extends AppCompatActivity {

    Button button;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);



        //COMPROBAR SI INTRODUJO CLAVE ANTES Y SI ES CORRECTA
        final SharedPreferences config_clave=getSharedPreferences("clave", Context.MODE_PRIVATE);

        if(config_clave.contains("fecha")){
            String fechaCaducidad = config_clave.getString("fecha", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFechaCaducidad = null;
            try {
                dateFechaCaducidad = sdf.parse(fechaCaducidad);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (new Date().after(dateFechaCaducidad)) {
                config_clave.edit().clear().commit();
            }
            else{
                startActivity(new Intent(Inicio.this, Principal.class));
                finish();
            }
        }

        setContentView(R.layout.inicio);

        traducirInterfaz();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEdit = (EditText) findViewById(R.id.inicioCodigo);
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
                    ResultSet rs = conexion.hacerConsulta("SELECT * FROM CLAVE WHERE valor = '"+contenido+"'");

                    if(rs.isBeforeFirst()) {
                        rs.next();

                        Boolean estado = rs.getBoolean("estado");

                        if(estado == true){
                            startActivity(new Intent(Inicio.this, Error.class));
                        }
                        else{
                            clave = rs.getString("valor");

                            conexion.hacerUpdate("UPDATE CLAVE SET estado=true WHERE valor = '"+clave+"'");

                            String fechaCad = rs.getString("fechaCaducidad");
                            SharedPreferences.Editor editor = config_clave.edit();
                            editor.putString("fecha", fechaCad);
                            editor.commit();

                            SharedPreferences config = getSharedPreferences("config", Context.MODE_PRIVATE);
                            if (config.contains("idioma")) {
                                Usuario.initInstance(config.getString("idioma", "Español"), config.getBoolean("lenguajeSimple", false),
                                        config.getBoolean("lenguajeSignos", false));
                                startActivity(new Intent(Inicio.this, Principal.class));
                            } else
                                startActivity(new Intent(Inicio.this, Configuracion.class));
                        }
                    }
                    else{
                        startActivity(new Intent(Inicio.this, Error.class));
                    }
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }


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
        SharedPreferences trads=getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.inicioTexto);
        mTextView.setText(trads.getString("inicioTexto", "Bienvenidos al museo Caja Granada, pregunta por el código de la aplicación en recepción para poder acceder."));

        mEdit = (EditText) findViewById(R.id.inicioCodigo);
        mEdit.setHint(trads.getString("inicioCodigo", "Escribe el código"));

        button = (Button)findViewById(R.id.inicioBoton);
        button.setText(trads.getString("inicioBoton", "ENVIAR"));
    }



}
