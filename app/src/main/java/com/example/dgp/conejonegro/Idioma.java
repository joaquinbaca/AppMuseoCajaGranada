package com.example.dgp.conejonegro;

/**
 * Created by alexr on 04/11/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Idioma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma);

        Button btn = (Button)findViewById(R.id.Espaniol);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Introducir guardar el idioma
                startActivity(new Intent(Idioma.this, Configuracion.class));
            }
        });

        btn = (Button)findViewById(R.id.Frances);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Introducir guardar el idioma
                startActivity(new Intent(Idioma.this, Configuracion.class));
            }
        });

        btn = (Button)findViewById(R.id.Ingles);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Introducir guardar el idioma
                startActivity(new Intent(Idioma.this, Configuracion.class));
            }
        });

        btn = (Button)findViewById(R.id.Italiano);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Introducir guardar el idioma
                startActivity(new Intent(Idioma.this, Configuracion.class));
            }
        });
    }
}
