package com.proyectomoviles.unimaps.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

/*import com.example.moviles.proyectomoviles.AdminSQLite;
import com.example.moviles.proyectomoviles.Objetos.Dependencia;
import com.example.moviles.proyectomoviles.Objetos.Empleado;
import com.example.moviles.proyectomoviles.Objetos.FirebaseReferences;
import com.example.moviles.proyectomoviles.Objetos.Oficina;
import com.example.moviles.proyectomoviles.Objetos.Sede;
import com.example.moviles.proyectomoviles.Objetos.Usuario;
import com.example.moviles.proyectomoviles.PassValidator;
import com.example.moviles.proyectomoviles.R;*/


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectomoviles.unimaps.Objetos.FirebaseReferences;
import com.proyectomoviles.unimaps.Objetos.Usuario;
import com.proyectomoviles.unimaps.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button Registrar;

    private EditText nombres;
    private EditText apellidos;
    private EditText correo;
    private EditText password;
    private EditText empresa;
    private EditText oficina;
    private Spinner sexo;

    private String nom;
    private String ape;
    private String cor;
    private String pas;
    private String emp;
    private String ofi;
    private String sex;

    //private AdminSQLite db;
    private View vista;

    private DatabaseReference myRef;
    private FirebaseAuth auth;

    private ProgressBar progressBar;

    private ArrayAdapter<CharSequence> adapter;

    private static final String TAG="Register";

    public Register() {
        // Required empty public constructor
    }

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

//        db = new AdminSQLite(getActivity().getApplicationContext());
        myRef = FirebaseDatabase.getInstance().getReference(FirebaseReferences.BD_REFERENCE);//agregado
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        /*myRef.child(FirebaseReferences.USUARIO_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Usuario valor=dataSnapshot.getValue(Usuario.class);
                Toast.makeText(getActivity().getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_SHORT).show();

                //telefono.setText(valor+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR",databaseError.getMessage());
            }
        });*/

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista= inflater.inflate(R.layout.register, container, false);

        nombres =(EditText)vista.findViewById(R.id.eNombres);
        apellidos =(EditText)vista.findViewById(R.id.eApellidos);
        correo =(EditText)vista.findViewById(R.id.eCorreo);
        empresa =(EditText)vista.findViewById(R.id.eEmpresa);
        oficina =(EditText)vista.findViewById(R.id.eOficina);
        password =(EditText)vista.findViewById(R.id.ePassword);
        sexo = (Spinner)vista.findViewById(R.id.spinnerSexo);

        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opcionsex, R.layout.spinner);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, R.layout.spinner_item);
        //spinner.setAdapter(adapter);

        adapter.setDropDownViewResource(R.layout.spinner);
        sexo.setAdapter(adapter);

        Registrar=(Button)vista.findViewById(R.id.bRegister);
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cor = correo.getText().toString().trim();
                pas = password.getText().toString().trim();

                if (vista != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(vista.getWindowToken(), 0);
                }

                if (TextUtils.isEmpty(cor)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pas)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pas.length() < 6) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(cor, pas)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity().getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                nom = nombres.getText().toString().trim();
                                ape = apellidos.getText().toString().trim();
                                emp = empresa.getText().toString().trim();
                                ofi = oficina.getText().toString().trim();
                                sex = sexo.getSelectedItem().toString();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    onAuthSuccess(task.getResult().getUser());
                                    //startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    //finish();


                                }
                            }
                        });

            }
        });


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

        return vista;
    }


    //Funcion agregada
    private void onAuthSuccess(FirebaseUser user){
        writeNewUser(user.getUid(), user.getEmail());
    }

    // Funcion para escribir Usuario en la base de datos con el id de usuario
    // dentro de users
    //Funcion agregada WriteUser
    private void writeNewUser(String userId, String email){
        Usuario usuario = new Usuario(nom, ape,email,sex,emp,ofi, getPhoneNumber());
        myRef.child(FirebaseReferences.USUARIO_REFERENCE).child(userId).setValue(usuario);
    }

    //Funcion Agregada

        private String usernameFromEmail(String email){
            if(email.contains("@")){
                return email.split("@")[0];
            }
            else{
                return email;
            }
        }

        //Funcion Agregada

        private boolean validateForm(){
            boolean result = true;
            if(TextUtils.isEmpty(correo.getText().toString())){
                correo.setError("Required");
                result = false;
            }
            else{
                correo.setError(null);
            }
            return result;
        }



    private String getPhoneNumber(){
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getLine1Number();
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

/*    public void registrarUsuario(){
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



        if(!nombres.isEmpty()&& !correo.isEmpty() && !apellidos.isEmpty() && !oficinas.isEmpty() && !sexos.isEmpty()){

            Toast.makeText(getActivity().getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();
            CambiaFragment(Login.class);


        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Complete los datos",Toast.LENGTH_SHORT).show();
        }


    }*/

    /*public boolean isCorreoValid(){

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(sexo.getText().toString());
        return matcher.matches();

    }*/

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
        void onFragmentInteraction(Uri uri);
    }

}