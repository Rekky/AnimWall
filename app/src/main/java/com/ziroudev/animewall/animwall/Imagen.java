package com.ziroudev.animewall.animwall;

/**
 * Created by Rekky on 25/03/2016.
 */
public class Imagen {
    private String nombre;
    private String dibujo;
    public static Imagen[] ITEMS;


    public Imagen(String nombre, String url) {
        this.nombre = nombre;
        this.dibujo = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return dibujo;
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
