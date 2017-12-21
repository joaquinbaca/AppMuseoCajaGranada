package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import android.media.MediaPlayer;
import android.widget.VideoView;

public class verElemento extends AppCompatActivity{

   private String id;
   private Elemento elemento;
   VideoView videoView;
   private Button botonListaSalas;
   private Button botonQR;
   private Button botonRutas;
   private Button botonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elemento);
        id = getIntent().getExtras().getString("id");//Aqui recibe el id que carga el qr cuando se crea

        videoView = (VideoView) findViewById(R.id.videoView);

        Uri uri = Uri.parse("http://webappmuseo.ddns.net:8742/videos/ILSEMedinaAzahara.mp4");
        videoView.setMediaController((new MediaController(this)));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


       Museo museo = Museo.getInstance();
       elemento = museo.getElemento(id);

       if(elemento != null) {
           TextView txtCambiado = (TextView) findViewById(R.id.elementoNombre);
           txtCambiado.setText(elemento.getNombre());

           txtCambiado = (TextView) findViewById(R.id.elementoTexto);
           txtCambiado.setText(elemento.getTexto());

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

       crearBotones();
    }

    public void crearBotones(){

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        botonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, verSalas.class));
                finish();
            }
        });

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, Configuracion.class));
                finish();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, lector.class));
                finish();
            }
        });

        botonRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verElemento.this, verRutas.class));
                finish();
            }
        });
        traducirInterfaz();
    }

    public void traducirInterfaz(){
        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.principalTexto);
        mTextView.setText(config.getString("principalTexto", "Configuración"));

        TextView mTextView2 = (TextView)findViewById(R.id.principalTextoQR);
        mTextView2.setText(config.getString("principalBotonQR", "Escanear QR"));

        TextView mTextView3 = (TextView)findViewById(R.id.principalTextoRutas);
        mTextView3.setText(config.getString("principalBotonRutas", "Ver Rutas"));

        TextView mTextView4 = (TextView)findViewById(R.id.principalTextoSalas);
        mTextView4.setText(config.getString("principalBotonSalas", "Ver Salas"));

    }

}
