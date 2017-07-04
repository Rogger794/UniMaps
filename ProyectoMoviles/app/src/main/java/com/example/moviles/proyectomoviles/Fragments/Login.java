package com.example.moviles.proyectomoviles.Fragments;

import android.content.Context;
//import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviles.proyectomoviles.AdminSQLite;
import com.example.moviles.proyectomoviles.R;
import com.example.moviles.proyectomoviles.Sesion;


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
    private AdminSQLite db;
    private Sesion sesion;
    private View vista;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Opciones.
     */
    // TODO: Rename and change types and number of parameters
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
        db = new AdminSQLite(getActivity().getApplicationContext());
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
        vista= inflater.inflate(R.layout.login, container, false);

        //Intent intent;

        correoLogin =(EditText)vista.findViewById(R.id.mailLogin);
        passLogin =(EditText)vista.findViewById(R.id.passLogin);

        login =(Button)vista.findViewById(R.id.login);
        register=(Button)vista.findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        if(sesion.loggedIn()){
            CambiaFragment(Main2Activity.class);
            //intent = new Intent(getActivity().getApplicationContext(),Main2Activity.class);
            //startActivity(intent);
            //finish();
            return null;
        }

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
                if (login()){
                    CambiaFragment(Main2Activity.class);
                    //intencion= new Intent(getActivity().getApplicationContext(), Main2Activity.class);
                    //startActivity(intencion );
                }
                break;

            case R.id.register:
                CambiaFragment(Register.class);
                break;
            default:
                break;
        }



    }


    public boolean login(){
        String correo =correoLogin.getText().toString();
        String password=passLogin.getText().toString();
        String[] datos;
        String[] datostmp;


        if(db.getUserLogin(correo,password)){

            sesion.setLoggedIn(true);
            datos = db.getUserValues(correo);
            sesion.setUserValues(datos[0],datos[1],datos[2]);
            datostmp = sesion.getUserValues();
            Toast.makeText(getActivity().getApplicationContext(), datostmp[0],Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Datos Erroneos",Toast.LENGTH_SHORT).show();
            return  false;
        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
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
