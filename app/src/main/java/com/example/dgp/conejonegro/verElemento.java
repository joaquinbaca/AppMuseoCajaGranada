package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;

public class verElemento extends AppCompatActivity{

   private String id;
   private Elemento elemento;
   ConexionBD conexion = null;
   private String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id = getIntent().getExtras().getString("id");//Aqui recibe el id que carga el qr cuando se crea
        Button btn = (Button)findViewById(R.id.elementoBotonMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, Principal.class));
                finish();
            }
        });
        Button btn1 = (Button)findViewById(R.id.elementoBotonSala);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, lector.class));
                finish();
            }
        });

       /* try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+id+"'");
            rs.next();
            String nombre = rs.getString("nombre");
            rs = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='"+id+"'");
            rs.next();
            String descripcion = rs.getString("texto");
            rs = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+id+"'");
            rs.next();
            ResultSet rsmedio = conexion.hacerConsulta("SELECT * FROM MEDIO WHERE idElemento='"+id+"'");
            String idMedio;
            if(rsmedio.isBeforeFirst()) {
                rsmedio.next();
                idMedio = rsmedio.getString("idmedio");
                ResultSet rsfoto = conexion.hacerConsulta("SELECT * FROM FOTO WHERE idElemento='"+idMedio+"'");
                rsfoto.next();
                imagen = rsfoto.getString("url");
            }
            else{
                imagen = "http://webappmuseo.ddns.net:8742/images/noimage.png";
            }
            elemento = new Elemento(nombre, descripcion, imagen, id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
*/

       Museo museo = Museo.getInstance();
       Elemento elemento = museo.getElemento(id);

       if(elemento != null) {
           TextView txtCambiado = (TextView) findViewById(R.id.elementoNombre);
           txtCambiado.setText(elemento.getNombre());

           txtCambiado = (TextView) findViewById(R.id.elementoTexto);
           txtCambiado.setText(elemento.getDescripcion());

           ImageView foto = (ImageView) findViewById(R.id.elementoImagen);

           URL imageUrl = null;
           try {
               imageUrl = new URL(elemento.getImagen());
               HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
               conn.connect();
               Bitmap loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
               foto.setImageBitmap(loadedImage);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

}
