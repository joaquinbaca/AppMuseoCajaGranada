package com.example.dgp.conejonegro;

/**
 * Created by alexr on 25/11/2017.
 */

public class Elemento {

    private String nombre;
    private String descripcion;

    public Elemento(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

}
