package com.example.dgp.conejonegro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//probandoq
/**
 * Created by alexr on 05/11/2017.
 */

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        Button button1 = (Button)findViewById(R.id.Salas);
        button1.setText(config.getString("PrincipalBoton1", "VER SALAS"));

        Button button2 = (Button)findViewById(R.id.QR);
        button2.setText(config.getString("PrincipalBoton2", "ESCANEAR QR"));

        Button button3 = (Button)findViewById(R.id.Rutas);
        button3.setText(config.getString("PrincipalBoton3", "VER RUTAS"));

        Button buttonConfig = (Button)findViewById(R.id.config);

        TextView mTextView = (TextView)findViewById(R.id.TextoConfig);
        mTextView.setText(config.getString("PrincipalBotonConf", "Configuración"));



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, lector.class));

            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, Configuracion.class));

            }
        });
    }
}
