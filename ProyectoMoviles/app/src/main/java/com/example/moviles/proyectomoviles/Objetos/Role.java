package com.example.moviles.proyectomoviles.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Role {
    int idRole;
    String nombreRole;

    public Role(){

    }

    public Role(int idRole, String nombreRole) {
        this.idRole = idRole;
        this.nombreRole = nombreRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getNombreRole() {
        return nombreRole;
    }
}
