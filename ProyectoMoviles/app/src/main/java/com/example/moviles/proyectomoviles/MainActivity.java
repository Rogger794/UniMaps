package com.example.moviles.proyectomoviles;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;


import com.example.moviles.proyectomoviles.Fragments.Login;
import com.example.moviles.proyectomoviles.Fragments.Register;
import com.example.moviles.proyectomoviles.Fragments.Configuraciones;
import com.example.moviles.proyectomoviles.Fragments.Mapa;
import com.example.moviles.proyectomoviles.Fragments.Opciones;
import com.example.moviles.proyectomoviles.Fragments.Pestanas;
import com.example.moviles.proyectomoviles.Fragments.Main2Activity;
import com.example.moviles.proyectomoviles.Fragments.Camara;
import com.example.moviles.proyectomoviles.Objetos.Dependencia;
import com.example.moviles.proyectomoviles.Objetos.FirebaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements Main2Activity.OnFragmentInteractionListener, Login.OnFragmentInteractionListener, Register.OnFragmentInteractionListener, Configuraciones.OnFragmentInteractionListener,Opciones.OnFragmentInteractionListener, Mapa.OnFragmentInteractionListener, Pestanas.OnFragmentInteractionListener, Camara.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Login.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(getFragmentManager().getBackStackEntryCount()>1)
            getFragmentManager().popBackStack();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}