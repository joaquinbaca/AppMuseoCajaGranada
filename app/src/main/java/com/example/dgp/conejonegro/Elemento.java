package com.example.dgp.conejonegro;

/**
 * Created by alexr on 25/11/2017.
 */

public class Elemento {

    private String nombre;
    private String descripcion;
    private String imagen;
    private String idElemento;

    public Elemento(String nombre, String descripcion, String imagen, String idElemento){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.idElemento = idElemento;
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
