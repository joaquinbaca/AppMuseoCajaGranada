package com.example.dgp.conejonegro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alexr on 25/11/2017.
 */

public class Elemento {

    private String nombre;
    private String descripcion;
    private String imagen;
    private String video;
    private String idElemento;
    private String texto;
    private Bitmap loadedImage;

    public Elemento(String nombre, String descripcion, String imagen, String idElemento, String texto, String video){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.idElemento = idElemento;
        this.texto = texto;
        this.video = video;
        URL imageUrl = null;
        try {
            imageUrl = new URL(getImagen());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
        URL imageUrl = null;
        try {
            imageUrl = new URL(getImagen());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(){
        return loadedImage;
    }

    public String getVideo(){
        return video;
    }

    public String getTexto(){
        return texto;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getIdElemento(){
        return idElemento;
    }

}
