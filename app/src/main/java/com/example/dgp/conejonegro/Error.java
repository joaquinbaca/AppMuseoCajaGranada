package com.example.dgp.conejonegro;

/**
 * Created by alexr on 02/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Error extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error);

        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.errorTexto);
        mTextView.setText(config.getString("errorTexto", "Parece que ha introducido mal el código, pregunte en recepción por el código de la aplicación para poder acceder."));

        Button button = (Button)findViewById(R.id.errorBoton);
        button.setText(config.getString("errorBoton", "VOLVER"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Error.this, Inicio.class));
            }
        });
    }
}
