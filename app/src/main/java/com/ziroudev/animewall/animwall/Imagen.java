package com.ziroudev.animewall.animwall;

import android.util.Log;

/**
 * Created by Rekky on 25/03/2016.
 */
public class Imagen {
    private String nombre;
    private String idDrawable;
    public static Imagen[] ITEMS;


    public Imagen(String nombre, String url) {
        this.nombre = nombre;
        this.idDrawable = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
    }



    public static Imagen getItem(int id) {
        for (Imagen item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
