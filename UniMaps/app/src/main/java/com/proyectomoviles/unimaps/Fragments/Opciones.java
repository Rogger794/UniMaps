package com.proyectomoviles.unimaps.Fragments;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.proyectomoviles.unimaps.R;

public class Opciones extends Fragment implements
        View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Opciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Opciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Opciones newInstance(String param1, String param2) {
        Opciones fragment = new Opciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.opciones, container, false);
        Button mapa = (Button) vista.findViewById(R.id.Mapa);
        mapa.setOnClickListener(this);
        Button camara = (Button) vista.findViewById(R.id.Camara);
        camara.setOnClickListener(this);
        Button orientacion=(Button) vista.findViewById(R.id.orientar);
        orientacion.setOnClickListener(this);
        return vista;
    }

    @Override
    public void onClick(View v) {
//        Intent intencion;
        if(v.getId() == R.id.Mapa)
            CambiaFragment(Mapa.class);
        else if(v.getId()==R.id.Camara){
            CambiaFragment2(Camara.class);
//            intencion = new Intent(getActivity(), Camara.class);
//        startActivity(intencion);
        }/*
        else if(v.getId()==R.id.orientar){
            //CambiaFragment3(Orientacion.class);
            Intent intencion = new Intent(getActivity(), Orientacion.class);
            startActivity(intencion);
        }*/
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

    private void CambiaFragment2(Class C){
        Fragment fragment=null;
        Class fragmentClass=C;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other layout.fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
