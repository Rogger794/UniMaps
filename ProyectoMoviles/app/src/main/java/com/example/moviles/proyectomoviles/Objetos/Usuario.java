package com.example.moviles.proyectomoviles.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Usuario {
    int idUsuario;
    int idRole;
    String nombreUsuario;

    public Usuario(){

    }

    public Usuario(int idUsuario, int idRole, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.idRole = idRole;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
