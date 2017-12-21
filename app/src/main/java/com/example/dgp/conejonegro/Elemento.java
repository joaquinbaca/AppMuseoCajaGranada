package com.example.dgp.conejonegro;

/**
 * Created by alexr on 25/11/2017.
 */

public class Elemento {

    private String nombre;
    private String descripcion;
    private String imagen;
    private String idElemento;
    private String texto;

    public Elemento(String nombre, String descripcion, String imagen, String idElemento, String texto){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.idElemento = idElemento;
        this.texto = texto;
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
