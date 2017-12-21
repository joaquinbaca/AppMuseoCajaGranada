package com.example.dgp.conejonegro;
//probando
/**
 * Created by joaqu on 29/10/2017.
 */
public class Usuario {
    String  idioma = " ";
    boolean lenguajeSimple;
    boolean lenguajeSignos;
    private static Usuario instance;

    private Usuario(String idioma, boolean lengSimple, boolean lengSignos){
        this.idioma=idioma;;
        this.lenguajeSignos=lengSignos;
        this.lenguajeSimple=lengSimple;
    }

    private void setDatos(String idioma, boolean lengSimple, boolean lengSignos){
        this.idioma=idioma;
        this.lenguajeSignos=lengSignos;
        this.lenguajeSimple=lengSimple;
    }

    public static void initInstance(String idioma, boolean lengSimple, boolean lengSignos){
        //Este es para cargar por formulario y el otro de clase persistente
        if(instance == null)
            instance = new Usuario(idioma, lengSimple, lengSignos);
        else
            instance.setDatos(idioma, lengSimple, lengSignos);
    }

    public static Usuario getInstance(){
        return instance;
    }

    public String getIdioma(){
        return idioma;
    }

    public boolean getLenguajeSimple(){
        return lenguajeSimple;
    }

    public boolean getLenguajeSignos(){
        return lenguajeSignos;
    }

}
