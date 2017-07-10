package com.proyectomoviles.unimaps.Fragments;

import android.content.Context;
//import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyectomoviles.unimaps.R;
import com.proyectomoviles.unimaps.Sesion;


public class Login extends Fragment implements
        View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    private EditText correoLogin;
    private EditText passLogin;
    private Button login;
    private Button register;
    //private AdminSQLite db;
    //private Sesion sesion;
    private View vista;

    FirebaseDatabase mDatabase;
    DatabaseReference myRef;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = new AdminSQLite(getActivity().getApplicationContext());
        //sesion = new Sesion(getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.login, container, false);

        correoLogin =(EditText)vista.findViewById(R.id.mailLogin);
        passLogin =(EditText)vista.findViewById(R.id.passLogin);

        login =(Button)vista.findViewById(R.id.login);
        register=(Button)vista.findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        /*if(sesion.loggedIn()){
            CambiaFragment(Navigation.class);
            return null;
        }*/

//        correoLogin.addTextChangedListener(new PassValidator(correoLogin) {
//            @Override
//            public void validate(EditText editText, String text) {
//                //Implementamos la validación que queramos
//
//
//                if(!isCorreoValid())
//                    correoLogin.setError( "No es correo valido" );
//            }
//        });
        return vista;
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
        transaction.replace(R.id.flContent, fragment).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        //View view = getActivity().getCurrentFocus();
        //esconder keyboard
        if (vista != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vista.getWindowToken(), 0);
        }

        switch(v.getId()){
            case R.id.login:
                login();
                    /*ValueEventListener valuelistener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario valor=dataSnapshot.getValue(Usuario.class);
                Toast.makeText(getActivity().getApplicationContext(),valor.getEmail(),Toast.LENGTH_SHORT).show();

                //telefono.setText(valor+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR",databaseError.getMessage());
            }
        };*/
/*
                    myRef.child(FirebaseReferences.USUARIO_REFERENCE).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario valor=dataSnapshot.getValue(Usuario.class);
                            Toast.makeText(getActivity().getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_SHORT).show();

                            //telefono.setText(valor+"");
                        }*/
                    //CambiaFragment(Navigation.class);
                    //intencion= new Intent(getActivity().getApplicationContext(), Navigation.class);
                    //startActivity(intencion );
                //}
                break;

            case R.id.register:
                CambiaFragment(Register.class);
                break;
            default:
                break;
        }

    }

    public void login(){
        String correo =correoLogin.getText().toString().trim();
        String password=passLogin.getText().toString().trim();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo,password);
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