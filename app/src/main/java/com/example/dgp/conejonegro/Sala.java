package com.example.dgp.conejonegro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by joaqu on 29/10/2017.
 */
public class Sala {
    private ArrayList<Elemento> elementos;
    private int planta;
    private String nombre;
    private String descripcion, imagen;
    private String idZona;
    private Bitmap loadedImage;

    Sala(ArrayList<Elemento> elementos, int planta, String nombre, String descripcion, String imagen, String idZona){
        this.elementos = new ArrayList();
        this.elementos = elementos;
        this.planta = planta;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.idZona = idZona;
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

    public String getIdZona(){
        return idZona;
    }

    public int getPlanta(){
        return planta;
    }

    public ArrayList<Elemento> getElementos(){
        return elementos;
    }

    public Elemento getElementoN(int n){
        return elementos.get(n);
    }

    public int getNElementos(){
        return elementos.size();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }
}
