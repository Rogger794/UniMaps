package com.example.moviles.proyectomoviles;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
import android.view.View;
import android.app.Activity;

/**
 * Created by broman on 06/06/17.
 */

public class Sesion {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;


    public Sesion(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("preferencias",context.MODE_PRIVATE);
        editor = prefs.edit();

    }

    public void setLoggedIn(boolean login){
        editor.putBoolean("logInMode",login);
        editor.commit();
    }

    public boolean loggedIn(){
        return prefs.getBoolean("logInMode", false);
    }

    public void setUserValues(String name, String lastname, String email){

        editor.putString("nameKey", name);
        editor.putString("lastnameKey",lastname);
        editor.putString("correoKey",email);

        editor.commit();
    }
    public String[] getUserValues(){
        String[] datos = {  prefs.getString("nameKey",null),
                            prefs.getString("lastnameKey",null),
                            prefs.getString("correoKey",null)};
        return datos;
    }
    public void limpiarPrefs(){
        editor.clear();
        editor.commit();
    }


}
