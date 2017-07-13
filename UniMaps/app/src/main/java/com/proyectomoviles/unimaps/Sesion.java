package com.proyectomoviles.unimaps;

import android.content.Context;
import android.content.SharedPreferences;

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

    /*public void setLoggedIn(boolean login){
        editor.putBoolean("logInMode",login);
        editor.commit();
    }*/

    /*public boolean loggedIn(){
        return prefs.getBoolean("logInMode", false);
    }*/

    public void setUserValues(String id,String name, String lastname, String email){
        editor.putString("idKey", id);
        editor.putString("nameKey", name);
        editor.putString("lastnameKey",lastname);
        editor.putString("correoKey",email);
        editor.putString("grupoKey",email);

        editor.commit();
    }

    public void setName(String name){

        editor.putString("nameKey", name);
        editor.commit();
    }

    public void setLastName(String lname){
        editor.putString("lastnameKey", lname);
        editor.commit();
    }

    public void setGrupo(String lname){
        editor.putString("grupoKey", lname);
        editor.commit();
    }

    public void setEmail(String ema){

        editor.putString("correoKey", ema);
        editor.commit();
    }

    public void setId(String id){

        editor.putString("idKey", id);
        editor.commit();
    }

    public String[] getUserValues(){
        String[] datos = {
                prefs.getString("idKey",null),
                prefs.getString("nameKey",null),
                prefs.getString("lastnameKey",null),
                prefs.getString("correoKey",null),
                prefs.getString("grupoKey",null)};
        return datos;
    }

    public String getId(){
        return prefs.getString("idKey",null);
    }

    public String getGrupo(){
        return prefs.getString("grupoKey",null);
    }

    public void limpiarPrefs(){
        editor.clear();
        editor.commit();
    }


}
