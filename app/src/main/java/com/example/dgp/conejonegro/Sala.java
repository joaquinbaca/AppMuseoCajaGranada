package com.example.dgp.conejonegro;

import java.util.ArrayList;

/**
 * Created by joaqu on 29/10/2017.
 */
public class Sala {
    private ArrayList<Elemento> elementos;
    private int planta;
    private String nombre;

    Sala(ArrayList<Elemento> elementos, int planta, String nombre){
        this.elementos = new ArrayList();
        this.elementos = elementos;
        this.planta = planta;
        this.nombre = nombre;
    }

    public int getPlanta(){
        return planta;
    }

    public ArrayList<Elemento> getElementos(){
        return elementos;
    }

    public Elemento getElementoN(int n){
        return elementos.get(n);
    }

    public int getNElementos(){
        return elementos.size();
    }

}
