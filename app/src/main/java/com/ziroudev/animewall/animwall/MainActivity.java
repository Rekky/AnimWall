package com.ziroudev.animewall.animwall;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private GridView gridView;
    private AdaptadorDeImagen adaptador;
    private String laImagen;

    public static Imagen[] ITEMS = {
            new Imagen("la uno", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png"),
            new Imagen("la dos", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png"),
            new Imagen("la tres", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png"),
            new Imagen("la cuatro", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png"),
            new Imagen("la cinco", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png"),
            new Imagen("la seis", "http://4.bp.blogspot.com/-O1DH4PGWWM4/VaJmMAfAotI/AAAAAAACLOY/sREKCPcPz1Q/s1600/GOD.EATER.2015.WEB.png")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONManager jsonManager = new JSONManager();
        laImagen = jsonManager.lectura;

        Log.d("hola","SE INICIA LA APP");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Operaciones http
        } else {
            // Mostrar errores
            android.app.FragmentManager fragmentManager = getFragmentManager();
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.mensaje = "Debes tener activa una conexion a Internet.";
            dialogo.show(fragmentManager, "tagAlerta");
        }

        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new AdaptadorDeImagen(this, ITEMS);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);

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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Imagen item = (Imagen) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, ActividadDetalle.class);
        intent.putExtra(ActividadDetalle.EXTRA_PARAM_ID, item.getIdDrawable());

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
