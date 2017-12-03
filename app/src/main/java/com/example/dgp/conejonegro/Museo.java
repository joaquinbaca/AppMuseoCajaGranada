package com.example.dgp.conejonegro;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexr on 30/11/2017.
 */

public class Museo {
    private static Museo instance = null;
    private ArrayList<Sala> salas;

    private Museo() {
        salas=new ArrayList<Sala>();
    }

    public static Museo getInstance() {
        if(instance == null)
            instance = new Museo();

        return instance;
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

}
