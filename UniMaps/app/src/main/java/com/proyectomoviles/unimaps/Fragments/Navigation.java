package com.proyectomoviles.unimaps.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.proyectomoviles.unimaps.MainActivity;
import com.proyectomoviles.unimaps.R;
import com.proyectomoviles.unimaps.Sesion;

public class Navigation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;


    private OnFragmentInteractionListener mListener;

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    private Cursor fila;
    private Sesion sesion;
    private FloatingActionButton fab;

    public Navigation() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Navigation newInstance(String param1, String param2) {
        Navigation fragment = new Navigation();
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
        //sesion.setUserValues((MainActivity)getActivity().get,datos[1],datos[2]);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("name");
            mParam2 = getArguments().getString("lastname");
            mParam3 = getArguments().getString("email");
            //sesion.setUserValues(mParam1,mParam2,mParam3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String name = this.getArguments().getString("message");
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
                                //sesion.setLoggedIn(false);
                                fab.setVisibility(View.GONE);
                                Toast.makeText(getActivity().getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                                //intencion = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                //startActivity(intencion);
                                FirebaseAuth.getInstance().signOut();
                                //CambiaFragment(Login.class);
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
        usuario.setText(datostmp[1]+" "+datostmp[2]);
        correo.setText(datostmp[3]);
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