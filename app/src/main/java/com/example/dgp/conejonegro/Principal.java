package com.example.dgp.conejonegro;

import android.app.ProgressDialog;
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
    Button button2;
    Button buttonConfig;
    Button buttonListaSalas;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog= new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        traducirInterfaz();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cargando");
                progressDialog.show();
                startActivity(new Intent(Principal.this, lector.class));
                finish();
            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cargando");
                progressDialog.show();
                startActivity(new Intent(Principal.this, Configuracion.class));
                finish();
            }
        });

        buttonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Cargando Salas");
                progressDialog.show();
                startActivity(new Intent(Principal.this, verSalas.class));
                finish();
            }
        });
    }

    public void traducirInterfaz(){
        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        buttonListaSalas = (Button)findViewById(R.id.principalBotonSalas);
       // buttonListaSalas.setText(config.getString("principalBotonSalas", "VER SALAS"));

        button2 = (Button)findViewById(R.id.principalBotonQR);
       // button2.setText(config.getString("principalBotonQR", "ESCANEAR QR"));

        Button button3 = (Button)findViewById(R.id.principalBotonRutas);
       // button3.setText(config.getString("principalBotonRutas", "VER RUTAS"));

        buttonConfig = (Button)findViewById(R.id.configuracionBoton);

        TextView mTextView = (TextView)findViewById(R.id.principalTexto);
        mTextView.setText(config.getString("principalTexto", "Configuración"));

        TextView mTextView2 = (TextView)findViewById(R.id.principalTextoQR);
        mTextView2.setText(config.getString("principalBotonQR", "Escanear QR"));

        TextView mTextView3 = (TextView)findViewById(R.id.principalTextoRutas);
        mTextView3.setText(config.getString("principalBotonRutas", "Ver Rutas"));

        TextView mTextView4 = (TextView)findViewById(R.id.principalTextoSalas);
        mTextView4.setText(config.getString("principalBotonSalas", "Ver Salas"));

    }
}
