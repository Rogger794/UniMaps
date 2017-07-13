package com.proyectomoviles.unimaps.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Usuario {
    //public int idRole;
    private String nombres;
    private String apellidos;
    private String correo;
    private String sexo;
    private String sede;
    private String dependencia;
    private String empresa;
    private String oficina;
    private String telefono;
    private String grupo;

    public Usuario(){

    }

    public Usuario(
            String nombres,
            String apellidos,
            String correo,
            String sexo,
            String empresa,
            String sede,
            String dependencia,
            String oficina,
            String telefono,
            String grupo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.sexo = sexo;
        this.empresa = empresa;
        this.sede = sede;
        this.dependencia = dependencia;
        this.oficina = oficina;
        this.telefono = telefono;
        this.grupo = grupo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getSede() {
        return sede;
    }

    public String getDependencia() {
        return dependencia;
    }

    public String getOficina() {
        return oficina;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGrupo() {
        return grupo;
    }
}
