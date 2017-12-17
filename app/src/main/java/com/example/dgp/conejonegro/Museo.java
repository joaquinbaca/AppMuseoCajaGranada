package com.example.dgp.conejonegro;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexr on 30/11/2017.
 */

public class Museo {
    private static Museo instance = null;
    private ArrayList<Sala> salas;
    private boolean completo;

    private Museo() {
        salas=new ArrayList<Sala>();
        completo = false;
    }

    public static Museo getInstance() {
        if(instance == null)
            instance = new Museo();

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

}
