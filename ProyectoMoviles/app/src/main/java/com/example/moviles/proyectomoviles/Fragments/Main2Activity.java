package com.example.moviles.proyectomoviles.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviles.proyectomoviles.R;
import com.example.moviles.proyectomoviles.Sesion;

public class Main2Activity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    private Cursor fila;
    private Sesion sesion;
    private FloatingActionButton fab;

    public Main2Activity() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Main2Activity newInstance(String param1, String param2) {
        Main2Activity fragment = new Main2Activity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesion = new Sesion(getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.navimain, container, false);

        setHasOptionsMenu(true);
        //getActivity().onCreateOptionsMenu(vista.createContextMenu());

        toolbar = (Toolbar) vista.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = Opciones.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent0, fragment).addToBackStack(null);
            transaction.commit();

        }

        fab = (FloatingActionButton) vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hacer algo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawerLayout = (DrawerLayout)vista.findViewById(R.id.drawer_layout);
        navView = (NavigationView)vista.findViewById(R.id.navview);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((getActivity()), drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        // Establecemos el actionbarToggle al drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Llamamos a la funcion syncState para que se muestre nuestro icono del menu.
        actionBarDrawerToggle.syncState();

        if (navView != null) {
            setupNavigation(navView);
        }

        return vista;
    }

    private void setupNavigation(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        Intent intencion;

                        if (menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                Toast.makeText(getActivity().getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                                CambiaFragment(Opciones.class);
                                break;

                            case R.id.menu_seccion_2:
                                Toast.makeText(getActivity().getApplicationContext(), "Lugares", Toast.LENGTH_SHORT).show();
                                CambiaFragment(Pestanas.class);
                                break;

                            case R.id.menu_opcion_2:
                                sesion.setLoggedIn(false);
                                fab.setVisibility(View.GONE);
                                Toast.makeText(getActivity().getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                                //intencion = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                //startActivity(intencion);
                                CambiaFragment(Login.class);
                                break;

                        }
/*                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                        drawerLayout.closeDrawer(GravityCompat.START);
*/

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
        getProfile();
    }

    private void getProfile() {
        //Para obtener los datos previamente guardados
        // simplemente empleamos el método getString()
        // del objeto SharedPreferences
        View hView =  navView.getHeaderView(0);
        TextView usuario = (TextView)hView.findViewById(R.id.Usuario0);
        TextView correo=(TextView)hView.findViewById(R.id.Correo0);
        String[] datostmp;
        datostmp=sesion.getUserValues();
        usuario.setText(datostmp[0]+" "+datostmp[1]);
        correo.setText(datostmp[2]);
    }


    private void CambiaFragment(Class C){
        Fragment fragment=null;
        Class fragmentClass=C;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent0, fragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Intent intencion;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getActivity().getApplicationContext(), "Configuracion", Toast.LENGTH_SHORT).show();
            CambiaFragment(Configuraciones.class);

            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}