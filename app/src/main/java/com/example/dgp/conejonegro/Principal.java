package com.example.dgp.conejonegro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
//probandoq
/**
 * Created by alexr on 05/11/2017.
 */

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        Button btn = (Button)findViewById(R.id.config);
        Button bt2 = (Button)findViewById(R.id.QR);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, Configuracion.class));

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, lector.class));

            }
        });
    }
}
