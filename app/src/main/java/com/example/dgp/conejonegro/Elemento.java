package com.example.dgp.conejonegro;

/**
 * Created by alexr on 25/11/2017.
 */

public class Elemento {

    private String nombre;
    private String descripcion;
    private String imagen;

    public Elemento(String nombre, String descripcion, String imagen){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
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

}
