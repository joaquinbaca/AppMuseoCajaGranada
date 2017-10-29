package com.example.dgp.conejonegro;

/**
 * Created by joaqu on 29/10/2017.
 */
public class Museo {
    private static Museo ourInstance = new Museo();

    public static Museo getInstance() {
        return ourInstance;
    }

    private Museo() {
    }
}
