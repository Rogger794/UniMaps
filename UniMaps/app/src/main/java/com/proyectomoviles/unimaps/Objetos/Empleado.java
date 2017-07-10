package com.proyectomoviles.unimaps.Objetos;

/**
 * Created by rogger on 06/07/17.
 */

public class Empleado {
    int idEmpleado;
    int idContrato;
    int idOficina;
    int DNI;
    String nombres;
    String apellidos;
    String sexo;
    int telefono;
    String email;

    public Empleado(){

    }

    public Empleado(int idEmpleado, int idContrato, int idOficina, int DNI, String nombres, String apellidos, String sexo, int telefono, String email) {
        this.idEmpleado = idEmpleado;
        this.idContrato = idContrato;
        this.idOficina = idOficina;
        this.DNI = DNI;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public int getIdOficina() {
        return idOficina;
    }

    public int getDNI() {
        return DNI;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
}
