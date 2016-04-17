package com.ziroudev.animewall.animwall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Rekky on 25/03/2016.
 */
public class AdaptadorDeImagen extends BaseAdapter{
    private Context context;
    //private Imagen[] urls;
    private ArrayList<Imagen> urls = new ArrayList<Imagen>();

    public AdaptadorDeImagen(Context context, ArrayList<Imagen> lista){
        this.context = context;
        this.urls = lista;
        Log.d("hola", "adapter:"+urls.get(0).getIdDrawable());
    }

    @Override
    public int getCount() {return urls.size();}

    @Override
    public Imagen getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenCoche = (ImageView) view.findViewById(R.id.imagen_coche);
        TextView nombreCoche = (TextView) view.findViewById(R.id.nombre_coche);

//        final Imagen item = getItem(position);
//        Glide.with(context)
//            .load(urls[position].getIdDrawable())
//            .into(imagenCoche);

        final Imagen item = getItem(position);
        Glide.with(context)
                .load(urls.get(position).getIdDrawable())
                .into(imagenCoche);

        nombreCoche.setText(item.getNombre());

        return view;
    }
}
