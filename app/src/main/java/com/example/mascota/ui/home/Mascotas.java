package com.example.mascota.ui.home;

import android.net.Uri;

public class Mascotas {


    private String Nombre;
    private String Imagen_perfil;
    private String Especie;
    private String FechaNacimiento;
    private String Color;
    private String NumeroHistoriaClinica;
    private String Raza;
    private String Sexo;

    public Mascotas(){

    }

    public Mascotas(String Nombre, String Imagen_perfil, String Especie, String FechaNacimiento, String Color, String NumeroHistoriaClinica, String Raza, String Sexo) {
        this.Nombre = Nombre;
        this.Imagen_perfil = Imagen_perfil;
        this.Especie = Especie;
        this.FechaNacimiento = FechaNacimiento;
        this.Color = Color;
        this.NumeroHistoriaClinica = NumeroHistoriaClinica;
        this.Raza = Raza;
        this.Sexo = Sexo;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getImagen_perfil() {
        return Imagen_perfil;
    }

    public void setImagen_perfil(String Imagen_perfil) {
        this.Imagen_perfil = Imagen_perfil;
    }

    public String getEspecie() {
        return Especie;
    }

    public void setEspecie(String Especie) {
        this.Especie = Especie;
    }

    public String  getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String  FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }




    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getNumeroHistoriaClinica() {
        return NumeroHistoriaClinica;
    }

    public void setNumeroHistoriaClinica(String NumeroHistoriaClinica) {
        this.NumeroHistoriaClinica = NumeroHistoriaClinica;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String Raza) {
        this.Raza = Raza;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }





    @Override
    public String toString() {
        return "Mascota{" +
                "Nombre='" + Nombre + '\'' +
                ", Imagen_perfil=" + Imagen_perfil +
                ", Especie=" + Especie +
                ", FechaNacimiento=" + FechaNacimiento +
                ", Color="+Color+
                ", NumeroHistoriaClinica="+NumeroHistoriaClinica+
                ", Raza="+Raza+
                ", Sexo="+Sexo+
                '}';
    }
}
