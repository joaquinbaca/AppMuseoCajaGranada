package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
public class Usuario {
    String  idioma = " ";
    boolean subtitulos;
    boolean audio;
    boolean lenguajeSimple;
    boolean lenguajeSignos;
    private static Usuario instance;

    private Usuario(String idioma, boolean subtitulos, boolean lengSimple, boolean lengSignos, boolean audio){
        this.idioma=idioma;
        this.subtitulos=subtitulos;
        this.audio=audio;
        this.lenguajeSignos=lengSignos;
        this.lenguajeSimple=lengSimple;
    }

    private void setDatos(String idioma, boolean subtitulos, boolean lengSimple, boolean lengSignos, boolean audio){
        this.idioma=idioma;
        this.subtitulos=subtitulos;
        this.audio=audio;
        this.lenguajeSignos=lengSignos;
        this.lenguajeSimple=lengSimple;
    }

    public static void initInstance(String idioma, boolean subtitulos, boolean lengSimple, boolean lengSignos, boolean audio){
        //Este es para cargar por formulario y el otro de clase persistente
        if(instance == null)
            instance = new Usuario(idioma, subtitulos, lengSimple, lengSignos, audio);
        else
            instance.setDatos(idioma, subtitulos, lengSimple, lengSignos, audio);
    }

    public static Usuario getInstance(){
        return instance;
    }

    public String getIdioma(){
        return "hola";
    }

    public boolean getLenguajeSimple(){
        return lenguajeSimple;
    }

    public boolean getLenguajeSignos(){
        return lenguajeSignos;
    }

    public boolean getSubtitulos(){
        return subtitulos;
    }

    public boolean getSonido(){
        return audio;
    }

}
