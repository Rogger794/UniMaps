package com.example.moviles.proyectomoviles;

/**
 * Created by visoc on 22/05/17.
 */

public class lugar {
    private int imagen;
    private String nombre;
    private String descripcion;
    public lugar(int imagen ,String nombre,String descripcion){
        this.imagen=imagen;
        this.nombre=nombre;
        this.descripcion=descripcion;
    }
    public int getImagen(){
        return imagen;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }

}
