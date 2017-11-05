package com.example.dgp.conejonegro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        Button btn = (Button)findViewById(R.id.Enviar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit   = (EditText)findViewById(R.id.codigo);
                String contenido = mEdit.getText().toString();
                // en vez de comprobar con "1234", comprobar con key de la bbdd
                if(contenido.equals("1234")) {
                    SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
                    if(config.contains("idioma")) {
                        Usuario.initInstance(config.getString("idioma","Español"),config.getBoolean("subtitulos",false),config.getBoolean("lenguajeSimple",false),
                                config.getBoolean("lenguajeSignos",false),config.getBoolean("sonido",false));
                        startActivity(new Intent(Inicio.this, Principal.class));
                    }
                    else
                        startActivity(new Intent(Inicio.this, Configuracion.class));
                }
                else
                    startActivity(new Intent(Inicio.this, Error.class));
                finish();
            }
        });

    }
}
