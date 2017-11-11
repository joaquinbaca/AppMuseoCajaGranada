package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class Elemento extends AppCompatActivity{

   private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id=getIntent().getExtras().getString("id");
        TextView txtCambiado = (TextView)findViewById(R.id.pruebaelemento);
        txtCambiado.setText(id);


    }
   public void cargarIdporQr(String id){
       this.id=id;
   }
}
