package com.example.dgp.conejonegro;

import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexr on 30/11/2017.
 */

public class Museo {
    private static Museo instance = null;
    private ArrayList<Sala> salas;
    private boolean completo;
    private String idioma;
    private boolean lenguajeSimple;
    private boolean lenguajeSignos;

    private Museo() {
        salas=new ArrayList<Sala>();
        completo = false;
        ActualizarMuseo("Español", false, false);
    }

    private Museo(String idioma, boolean lenguajeSimple, boolean lenguajeSignos) {
        salas=new ArrayList<Sala>();
        completo = false;

        ActualizarMuseo(idioma, lenguajeSimple, lenguajeSimple);
    }

    public static Museo getInstance() {
        if(instance == null) {
            instance = new Museo();
        }
        return instance;
    }

    public static Museo getInstance(String idioma, boolean lenguajeSimple, boolean lenguajeSignos) {
        if(instance == null)
            instance = new Museo(idioma, lenguajeSimple, lenguajeSimple);
        else{
            if(!instance.idioma.equals(idioma) || !instance.lenguajeSimple || lenguajeSimple || !instance.lenguajeSignos == lenguajeSignos){
                instance.ActualizarMuseo(idioma, lenguajeSimple, lenguajeSignos);
            }
        }
        return instance;
    }

    public boolean getCompleto(){
        return completo;
    }

    public void setCompleto(boolean completo){
        this.completo = completo;
    }

    public void setSalas(ArrayList<Sala> salas){
        this.salas=salas;
    }

    public ArrayList<Sala> getSalas(){
        return salas;
    }

    public Sala getSala(String id){
        Sala sala = null;
        for (Sala s : salas){
            if(s.getIdZona().equals(id))
                sala = s;
        }
        return sala;
    }

    public ArrayList<Sala> getSalasFiltro(String query){
        ArrayList<Sala> salas_elegidas = new ArrayList<>();
        if(query.equals("") || query.equals(" "))
            salas_elegidas=salas;
        else {
            for (Sala s : salas) {
                if (s.getNombre().contains(query.toLowerCase()) || s.getDescripcion().contains(query.toLowerCase()) || query.toLowerCase().equals(s.getDescripcion())|| query.toLowerCase().equals(s.getNombre().toLowerCase()) )
                    salas_elegidas.add(s);
                Log.v(s.getIdZona(), s.getNombre().toLowerCase());
                Log.v("query", query.toLowerCase());
            }
        }
        return salas_elegidas;
    }

    public Elemento getElemento(String idElemento){
        for (Sala s : salas){
            for (Elemento e : s.getElementos()){
                if(e.getIdElemento().equals(idElemento))
                    return e;
            }
        }
        return null;
    }

    public ArrayList<Elemento> getElementosFiltro(String query, Sala sala){
        ArrayList<Elemento> elementos = sala.getElementos();
        ArrayList<Elemento> resultados = new ArrayList<>();
        for(Elemento e : elementos){
            if(e.getNombre().contains(query.toLowerCase()) || e.getDescripcion().contains(query.toLowerCase()) || query.toLowerCase().equals(e.getDescripcion())|| query.toLowerCase().equals(e.getNombre().toLowerCase()) )
                resultados.add(e);
        }
        return resultados;
    }

    public void ActualizarMuseo(String idioma, boolean lenguajeSimple, boolean lenguajeSignos){

        this.idioma = idioma;
        this.lenguajeSimple = lenguajeSimple;
        this.lenguajeSignos = lenguajeSignos;

        ConexionBD conexion = null;
        Sala s;
        ArrayList<Sala> salas = new ArrayList<Sala>();
        try {
            conexion = new ConexionBD();

            ResultSet rs = conexion.hacerConsulta("SELECT  idIdioma FROM IDIOMA WHERE nombre = '" + idioma + "'" );
            rs.next();
            int idIdioma = rs.getInt("idIdioma");

            ResultSet rsZona = conexion.hacerConsulta("SELECT * FROM ZONA");

            while (rsZona.next()) {
                String idZona = rsZona.getString("idZona");
                String nombre = rsZona.getString("nombre");
                String imagen = rsZona.getString("imagen");
                String descripcionSala = "";
                int planta = 0;
                ArrayList<Elemento> elementos = new ArrayList<Elemento>();

                ResultSet rsSala = conexion.hacerConsulta("SELECT * FROM SALA WHERE idZona='" + idZona + "'");
                if (rsSala.isBeforeFirst()) {
                    rsSala.next();
                    planta = rsSala.getInt("planta");
                }

                ResultSet rsDescripcionSala = conexion.hacerConsulta("SELECT * FROM `DESCRIPCION-ZONA` WHERE (idZona='" + idZona + "' AND idIdioma='" + idIdioma + "')");
                if(!rsDescripcionSala.isBeforeFirst()){
                    ResultSet rsDescripcionSala2 = conexion.hacerConsulta("SELECT * FROM `DESCRIPCION-ZONA` WHERE (idZona='" + idZona + "' AND idIdioma=1)");
                    if(rsDescripcionSala2.isBeforeFirst()){
                        rsDescripcionSala2.next();
                        descripcionSala = rsDescripcionSala2.getString("texto");
                    }
                }
                else{
                    rsDescripcionSala.next();
                    descripcionSala = rsDescripcionSala.getString("texto");
                }

                ResultSet rsZonaElemento = conexion.hacerConsulta("SELECT * FROM ZONA_ELEMENTO WHERE idZona='" + idZona + "'");
                if (rsZonaElemento.isBeforeFirst()) {
                    while (rsZonaElemento.next()) {
                        String idElemento = rsZonaElemento.getString("idElemento");
                        String nombreElemento = "";
                        String descripcionElemento = "";
                        String url_foto = "http://webappmuseo.ddns.net:8742/images/noimage.png";
                        String textoElemento = "";
                        String url_video = "";

                        ResultSet rsElemento = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='" + idElemento + "'");
                        if (rsElemento.isBeforeFirst()) {
                            while (rsElemento.next()) {
                                nombreElemento = rsElemento.getString("nombre");

                                ResultSet rsDescripcion = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE (idElemento='" + idElemento + "' AND idIdioma='" + idIdioma + "')");
                                if(!rsDescripcion.isBeforeFirst()){
                                    ResultSet rsDescripcion2 = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE (idElemento='" + idElemento + "' AND idIdioma=1)");
                                    if(rsDescripcion2.isBeforeFirst()){
                                        rsDescripcion2.next();
                                        descripcionElemento = rsDescripcion2.getString("texto");
                                    }
                                }
                                else{
                                    rsDescripcion.next();
                                    descripcionElemento = rsDescripcion.getString("texto");
                                }

                                ResultSet rsMedio = conexion.hacerConsulta("SELECT * FROM MEDIO WHERE idElemento='" + idElemento + "'");
                                if (rsMedio.isBeforeFirst()) {
                                    while(rsMedio.next()) {
                                        String idMedio = rsMedio.getString("idMedio");
                                        String idTipoMedio = rsMedio.getString("idTipoMedio");

                                        if(idTipoMedio.equals("1") && lenguajeSimple){
                                            ResultSet rsTextoSimple = conexion.hacerConsulta("SELECT * FROM TEXTOSIMPLIFICADO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "')");
                                            if (rsTextoSimple.isBeforeFirst()) {
                                                rsTextoSimple.next();
                                                textoElemento = rsTextoSimple.getString("texto");
                                            }
                                        }

                                        if(idTipoMedio.equals("2") && textoElemento.equals("")){
                                            ResultSet rsTextoNormal = conexion.hacerConsulta("SELECT * FROM TEXTO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "')");
                                            if(!rsTextoNormal.isBeforeFirst()){
                                                ResultSet rsTextoNormal2 = conexion.hacerConsulta("SELECT * FROM TEXTO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "')");
                                                if(rsTextoNormal2.isBeforeFirst()){
                                                    rsTextoNormal2.next();
                                                    textoElemento = rsTextoNormal2.getString("texto");
                                                }
                                            }
                                            else{
                                                rsTextoNormal.next();
                                                textoElemento = rsTextoNormal.getString("texto");
                                            }
                                        }

                                        if(idTipoMedio.equals("3")){
                                            ResultSet rsVideo = conexion.hacerConsulta("SELECT * FROM VIDEO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "' AND tipoVideo=1)");
                                            if(!rsVideo.isBeforeFirst()){
                                                ResultSet rsVideo2 = conexion.hacerConsulta("SELECT * FROM TEXTO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "'AND tipoVideo=1)");
                                                if(rsVideo2.isBeforeFirst()){
                                                    rsVideo2.next();
                                                    url_video = rsVideo2.getString("url");
                                                }
                                            }
                                            else{
                                                rsVideo.next();
                                                url_video  = rsVideo.getString("url");
                                            }

                                            if(lenguajeSignos){
                                                ResultSet rsVideo3 = conexion.hacerConsulta("SELECT * FROM VIDEO WHERE (idMedio='" + idMedio + "' AND idIdioma='" + idIdioma + "'AND tipoVideo=2)");
                                                if(rsVideo3.isBeforeFirst()){
                                                    rsVideo3.next();
                                                    url_video = rsVideo3.getString("url");
                                                }
                                            }

                                        }

                                        if(idTipoMedio.equals("4")){
                                            ResultSet rsFoto = conexion.hacerConsulta("SELECT * FROM FOTO WHERE idMedio='" + idMedio + "'");
                                            if (rsFoto.isBeforeFirst()) {
                                                rsFoto.next();
                                                url_foto = rsFoto.getString("url");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Elemento e = new Elemento(nombreElemento, descripcionElemento, url_foto, idElemento, textoElemento, url_video);
                        elementos.add(e);
                    }
                }
                s = new Sala(elementos, planta, nombre, descripcionSala, imagen, idZona);
                salas.add(s);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        try {
            conexion.cerrarBasedeDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSalas(salas);
        setCompleto(true);
    }

}
