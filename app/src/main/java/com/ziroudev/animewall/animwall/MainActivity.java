package com.ziroudev.animewall.animwall;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private GridView gridView;
    public AdaptadorDeImagen adaptador;
    private String pathWeb = "http://pellejo.net46.net/index.php";
    private String pathImagenes = "http://pellejo.net46.net/wallpapers/";
//    private String pathWeb = "http://192.168.0.201:81/pellejo/index.php";
//    private String pathImagenes = "http://192.168.0.201:81/pellejo/wallpapers/";
    public ArrayList<Imagen> lista = new ArrayList<Imagen>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // ADBanner
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequestBanner = new AdRequest.Builder().build();
            mAdView.loadAd(adRequestBanner);



            Log.d("test","SE INICIA LA APP");

            try {
                DownloadFileTask dwf = new DownloadFileTask();
                dwf.pathWeb = pathWeb;
                dwf.pathImagenes = pathImagenes;
                lista = dwf.execute().get();

//                Log.d("anim","ELOBJECTO_IMG:"+lista.get(1));

            } catch (Exception e) {
                e.printStackTrace();
            }

            gridView = (GridView) findViewById(R.id.grid);
            adaptador = new AdaptadorDeImagen(getApplicationContext(), lista);
            gridView.setAdapter(adaptador);
            gridView.setOnItemClickListener(this);


        } else {
            // Mostrar errores
            android.app.FragmentManager fragmentManager = getFragmentManager();
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.mensaje = "Debes tener activa una conexion a Internet.";
            dialogo.show(fragmentManager, "tagAlerta");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Imagen item = (Imagen) parent.getItemAtPosition(position);

            Log.d("test","Se ha seleccionado"+item.getImagen());

            Intent intent = new Intent(this, ActividadDetalle.class);
            intent.putExtra(ActividadDetalle.EXTRA_PARAM_IMG, item.getImagen());
            intent.putExtra(ActividadDetalle.EXTRA_PARAM_NOM, item.getNombre());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat activityOptions =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this,
                                new Pair<View, String>(view.findViewById(R.id.imagen_coche),
                                        ActividadDetalle.VIEW_NAME_HEADER_IMAGE)
                        );
                ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
            } else
                startActivity(intent);


}

}
