package com.ziroudev.animewall.animwall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorDeImagen extends BaseAdapter{

    private Context context;
    private ArrayList<Imagen> urls = new ArrayList<Imagen>();

    public AdaptadorDeImagen(Context context, ArrayList<Imagen> lista){
        this.context = context;
        this.urls = lista;
        Log.d("test", "adapter:"+urls.get(0).getImagen());
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

        final ImageView miniImagen = (ImageView) view.findViewById(R.id.imagen_coche);
//        final TextView nombreMiniImagen = (TextView) view.findViewById(R.id.nombre_coche);

        final Imagen item = getItem(position);

        Picasso.with(context)
                .load(urls.get(position).getImagen())
                .placeholder(R.drawable.ic_menu_camera)
                .into(miniImagen);

//        nombreMiniImagen.setText(item.getNombre());


        return view;
    }
}
