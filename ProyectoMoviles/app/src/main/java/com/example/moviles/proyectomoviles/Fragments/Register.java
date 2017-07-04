package com.example.moviles.proyectomoviles.Fragments;

import android.content.Context;
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
import android.widget.Toast;

import com.example.moviles.proyectomoviles.AdminSQLite;
import com.example.moviles.proyectomoviles.PassValidator;
import com.example.moviles.proyectomoviles.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Fragment implements
        View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button logUp;
    private Button logIn;


    private EditText correoLogup;
    private EditText passLogup;
    private EditText nameLogup;
    private EditText repassLogup;
    private EditText lastnameLogup;
    private AdminSQLite db;
    private View vista;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Opciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.register, container, false);

        correoLogup =(EditText)vista.findViewById(R.id.correoLogup);
        passLogup =(EditText)vista.findViewById(R.id.passLogup);
        nameLogup =(EditText)vista.findViewById(R.id.nameLogup);
        lastnameLogup =(EditText)vista.findViewById(R.id.lastnameLogup);
        repassLogup =(EditText)vista.findViewById(R.id.repassLogup);


        passLogup.addTextChangedListener(new PassValidator(passLogup) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( text.length() < 8 )
                    passLogup.setError( "La contraseña es muy corta" );
            }
        });

//        repassLogup.addTextChangedListener(new PassValidator(repassLogup) {
//            @Override
//            public void validate(EditText editText, String text) {
//                //Implementamos la validación que queramos
//                if( !text.equals(passLogup.getText().toString()) )
//                    repassLogup.setError( "La contraseña no es la misma" );
//            }
//        });

        correoLogup.addTextChangedListener(new PassValidator(correoLogup) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos

                if(!isCorreoValid())
                    correoLogup.setError( "No es correo valido" );
            }
        });

        logUp = (Button)vista.findViewById(R.id.logup);
        logUp.setOnClickListener(this);

        logIn = (Button)vista.findViewById(R.id.login);
        logIn.setOnClickListener(this);

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

        String nombre =nameLogup.getText().toString();
        String lastname =lastnameLogup.getText().toString();
        String correo =correoLogup.getText().toString();
        String password =passLogup.getText().toString();
        String repassword =repassLogup.getText().toString();
        boolean confPass =password.equals(repassword);
        boolean tamPass = password.length()<8;

        if (vista != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vista.getWindowToken(), 0);
        }
        switch(v.getId()){

            case R.id.logup :
                registrarUsuario();
                break;

            case R.id.login:
                CambiaFragment(Login.class);
                break;

            default:
                break;

        }

    }


    public void registrarUsuario(){
        //Intent intencion;
        String name = nameLogup.getText().toString();
        String lastname = lastnameLogup.getText().toString();
        String email = correoLogup.getText().toString();
        String password =passLogup.getText().toString();
        String repassword =repassLogup.getText().toString();

        boolean confPass =password.equals(repassword);
        boolean tamPass = password.length()<8;

        if(!name.isEmpty()&& !email.isEmpty() && !lastname.isEmpty() && !password.isEmpty() && !repassword.isEmpty() && confPass && !tamPass && isCorreoValid()){
            db.addUser(name,lastname,email,password);
            Toast.makeText(getActivity().getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();
            CambiaFragment(Login.class);
            //intencion= new Intent(getActivity().getApplicationContext(),MainActivity.class);
            //startActivity(intencion );
            //finish();
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Complete los datos",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isCorreoValid(){

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(correoLogup.getText().toString());
        return matcher.matches();

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

