package com.example.moviles.proyectomoviles.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviles.proyectomoviles.AdminSQLite;
import com.example.moviles.proyectomoviles.Objetos.Dependencia;
import com.example.moviles.proyectomoviles.Objetos.Empleado;
import com.example.moviles.proyectomoviles.Objetos.FirebaseReferences;
import com.example.moviles.proyectomoviles.Objetos.Oficina;
import com.example.moviles.proyectomoviles.Objetos.Sede;
import com.example.moviles.proyectomoviles.PassValidator;
import com.example.moviles.proyectomoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


    private EditText sexo;
    private EditText email;
    private EditText dni;
    //private EditText passLogup;
    private EditText nameLogup;
    private EditText oficina;
    private EditText lastnameLogup;
    private EditText telefono;
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

        nameLogup =(EditText)vista.findViewById(R.id.nameLogup);
        lastnameLogup =(EditText)vista.findViewById(R.id.lastnameLogup);
        sexo =(EditText)vista.findViewById(R.id.sexo);
        email =(EditText)vista.findViewById(R.id.correo);
        dni =(EditText)vista.findViewById(R.id.dni);
        oficina =(EditText)vista.findViewById(R.id.oficina);
        telefono =(EditText)vista.findViewById(R.id.telefono);

        /*passLogup.addTextChangedListener(new PassValidator(passLogup) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( text.length() < 8 )
                    passLogup.setError( "La contraseña es muy corta" );
            }
        });*/

//        repassLogup.addTextChangedListener(new PassValidator(repassLogup) {
//            @Override
//            public void validate(EditText editText, String text) {
//                //Implementamos la validación que queramos
//                if( !text.equals(passLogup.getText().toString()) )
//                    repassLogup.setError( "La contraseña no es la misma" );
//            }
//        });

        /*sexo.addTextChangedListener(new PassValidator(sexo) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos

                if(!isCorreoValid())
                    sexo.setError( "No es correo valido" );
            }
        });*/

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

        /*String nombre =nameLogup.getText().toString();
        String lastname =lastnameLogup.getText().toString();
        String sexos= sexo.getText().toString();
        String correo
        String password =passLogup.getText().toString();
        String oficinas =oficina.getText().toString();
        //boolean confPass =password.equals(repassword);
        //boolean tamPass = password.length()<8;
*/
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
        oficina =(EditText)vista.findViewById(R.id.oficina);
        telefono =(EditText)vista.findViewById(R.id.telefono);

        String nombres = nameLogup.getText().toString();
        String apellidos = lastnameLogup.getText().toString();
        String sexos = sexo.getText().toString();
        String correo = email.getText().toString();
        int ddni=Integer.parseInt(dni.getText().toString());
        String oficinas=oficina.getText().toString();
        int fono=Integer.parseInt(telefono.getText().toString());


        //String Oficina
        //String password =passLogup.getText().toString();
        //String repassword =repassLogup.getText().toString();


        //boolean confPass =password.equals(repassword);
        //boolean tamPass = password.length()<8;

        if(!nombres.isEmpty()&& !correo.isEmpty() && !apellidos.isEmpty() && !oficinas.isEmpty() && !sexos.isEmpty() && isCorreoValid()){
            //db.addUser(nombres,apellidos,email,password);
            //intencion= new Intent(getActivity().getApplicationContext(),MainActivity.class);
            //startActivity(intencion );
            //finish();

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            /*ValueEventListener valuelistener=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Dependencia dependencia=dataSnapshot.getValue(Dependencia.class);
                    Log.i("DATOS",dataSnapshot.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ERROR",databaseError.getMessage());
                }
            };*/
            DatabaseReference myRef=database.getReference(FirebaseReferences.SEDE_REFERENCE);
            Sede sede=new Sede((int)(System.currentTimeMillis()), "Uni", 20, "Rimac, Lima 25, Peru, Av. Tupac Amaru 210");
            myRef=myRef.child(FirebaseReferences.SEDE_REFERENCE);
            myRef.push().setValue(sede);

            Dependencia dependencia=new Dependencia((int)(System.currentTimeMillis()),"Ciencias", (int)(System.currentTimeMillis()),12, sede.getIdSede());
            myRef=myRef.child(FirebaseReferences.DEPENDENCIA_REFERENCE);
            myRef.push().setValue(dependencia);

            Oficina oficina=new Oficina((int)(System.currentTimeMillis()), sede.getIdSede(), dependencia.getIdDependencia(), 8, "Estadistica");
            myRef=myRef.child(FirebaseReferences.OFICINA_REFERENCE);
            myRef.push().setValue(oficina);

            Empleado empleado=new Empleado((int)(System.currentTimeMillis()), (int)(System.currentTimeMillis()), oficina.getIdOficina(), ddni, nombres, apellidos, sexos, fono, correo);
            myRef=myRef.child(FirebaseReferences.EMPLEADO_REFERENCE);
            myRef.push().setValue(empleado);

            Toast.makeText(getActivity().getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();
            CambiaFragment(Login.class);


        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Complete los datos",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isCorreoValid(){

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(sexo.getText().toString());
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

