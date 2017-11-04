package com.example.dgp.conejonegro;

import android.content.Intent;
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
                if(contenido.equals("1234"))
                    startActivity(new Intent(Inicio.this, Idioma.class)); // En vez de ir a idioma directamente, ir a principal si ya se eligió idioma y opciones alguna vez
                else
                    startActivity(new Intent(Inicio.this, Error.class));
            }
        });

    }
}
