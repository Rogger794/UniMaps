package com.proyectomoviles.unimaps;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyectomoviles.unimaps.Fragments.Login;
import com.proyectomoviles.unimaps.Fragments.Mapa;
import com.proyectomoviles.unimaps.Fragments.Navigation;
import com.proyectomoviles.unimaps.Fragments.Register;
import com.proyectomoviles.unimaps.Fragments.Opciones;
import com.proyectomoviles.unimaps.Fragments.Pestanas;
import com.proyectomoviles.unimaps.Fragments.Configuraciones;
import com.proyectomoviles.unimaps.Objetos.FirebaseReferences;
import com.proyectomoviles.unimaps.Objetos.Usuario;



/*
import com.example.moviles.proyectomoviles.Fragments.Mapa;
import com.example.moviles.proyectomoviles.Fragments.Camara;*/

public class MainActivity extends AppCompatActivity implements
        Login.OnFragmentInteractionListener,
        Register.OnFragmentInteractionListener,
        Navigation.OnFragmentInteractionListener,
        Configuraciones.OnFragmentInteractionListener,
        Mapa.OnFragmentInteractionListener,
        Pestanas.OnFragmentInteractionListener,
        Opciones.OnFragmentInteractionListener {

    private DatabaseReference myRef;
    private Sesion sesion;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String[] datos=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sesion = new Sesion(getApplicationContext());
        myRef=FirebaseDatabase.getInstance().getReference(FirebaseReferences.BD_REFERENCE).child(FirebaseReferences.USUARIO_REFERENCE);;

        if (savedInstanceState == null) {
            mAuthListener=new FirebaseAuth.AuthStateListener(){
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Fragment fragment = null;
                    Class fragmentClass = null;
                    if(user==null){
                        fragmentClass = Login.class;
                    }
                    else{
                        DatabaseReference usuario = myRef.child(user.getUid());

                        DatabaseReference name = usuario.child("nombres");
                        DatabaseReference lastname = usuario.child("apellidos");

                        name.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                datos[0] = dataSnapshot.getValue(String.class);

                                //profileName.setText(username);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"The read failed: " + databaseError.getCode(),Toast.LENGTH_SHORT).show();
                                //System.out.println();
                            }
                        });

                        lastname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                datos[1] = dataSnapshot.getValue(String.class);
                                //TextView profileName = (TextView) v.findViewById(R.id.profileName);
                                //profileName.setText(username);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });

                        datos[2]=user.getEmail();
                        // Attach a listener to read the data at our posts reference

                        Toast.makeText(getApplicationContext(),datos[0]+" "+datos[1]+" "+datos[2],Toast.LENGTH_SHORT).show();

                        //if(db.getUserLogin(correo,password)){
                        sesion.setUserValues(datos[0],datos[1],datos[2]);
                        //datostmp = sesion.getUserValues();

                        fragmentClass = Navigation.class;
                    }
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                }
            };
        }
    }

    /*public String [] getUserValues (DatabaseReference us){
        String[] datos= null;
        //datos = new String[] {us.get,c.getString(1),c.getString(2)};

        return datos;
    }*/


    @Override
    protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mAuthListener!=null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

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

    @Override
    public void onFragmentInteraction(Uri uri) {}
}