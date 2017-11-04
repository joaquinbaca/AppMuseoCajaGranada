package com.example.dgp.conejonegro;

/**
 * Created by alexr on 04/11/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        Spinner spinner = (Spinner) findViewById(R.id.spIdioma);

        String[] items = new String[] { "Español", "Ingles", "Frances", "Italiano" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);



    }
}