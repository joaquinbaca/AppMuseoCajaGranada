package com.example.dgp.conejonegro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class verRutas extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rutas);

        /*Button btn1 = (Button)findViewById(R.id.salaBotonQR);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verRutas.this, lector.class));
                finish();
            }
        });

        Button btn2 = (Button)findViewById(R.id.salasconfiguracionBoton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verRutas.this, Configuracion.class));
                finish();
            }
        });*/

        RutasFragment rutasFragment = (RutasFragment) getSupportFragmentManager().findFragmentById(R.id.rutas_container);
        if (rutasFragment == null) {
            rutasFragment = RutasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.rutas_container, rutasFragment).commit();
        }

    }
}
