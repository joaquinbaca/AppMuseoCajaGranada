package com.example.dgp.conejonegro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        EditText myTextBox = (EditText) findViewById(R.id.codigo);
        myTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intent myIntent;
                if(s.equals("1234"))
                    /*myIntent = new Intent(R.layout.inicio.getContext(), idioma.class); //Esto es para cambiar a la pestaña idioma*/
                else
                    /*myIntent = new Intent(CurrentActivity.this.getContext(), Error.class);//Esto es para cambiar a la pestaña error*/
                startActivityForResult(myIntent, 0); // Te envia a la elegida
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
