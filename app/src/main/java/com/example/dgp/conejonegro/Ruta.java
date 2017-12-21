package com.example.dgp.conejonegro;

import java.util.ArrayList;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class Ruta {

    private String idRuta;
    private String nombre;
    private String descripcion;
    private ArrayList<Elemento> elementos;

    Ruta(ArrayList<Elemento> elementos, String nombre, String descripcion, String idRuta){
        this.elementos = new ArrayList();
        this.elementos = elementos;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idRuta = idRuta;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String idRuta(){
        return idRuta;
    }

    public ArrayList<Elemento> getElementos(){
        return elementos;
    }

}
