package com.example.mascota.ui.vermascotas;

public class VerMascotas {

    private String Nombre;
    private String Imagen_perfil;
    private String Especie;
    private String FechaNacimiento;
    private String Sexo;
    private String NumeroHistoriaClinica;
    private String Raza;

    public VerMascotas(){

    }

    public VerMascotas(String Nombre, String Imagen_perfil, String Especie, String FechaNacimiento, String Sexo, String NumeroHistoriaClinica, String Raza) {
        this.Nombre = Nombre;
        this.Imagen_perfil = Imagen_perfil;
        this.Especie = Especie;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo=Sexo;
        this.NumeroHistoriaClinica=NumeroHistoriaClinica;
        this.Raza=Raza;
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

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String Raza) {
        this.Raza = Raza;
    }


    public String getNumeroHistoriaClinica() {
        return NumeroHistoriaClinica;
    }

    public void setNumeroHistoriaClinica(String NumeroHistoriaClinica) {
        this.NumeroHistoriaClinica = NumeroHistoriaClinica;
    }



    @Override
    public String toString() {
        return "Mascota{" +
                "Nombre='" + Nombre + '\'' +
                ", Imagen_perfil=" + Imagen_perfil +
                ", Especie=" + Especie +
                ", FechaNacimiento=" + FechaNacimiento +
                ", Sexo="+Sexo+
                ", NumeroHistoriaClinica="+NumeroHistoriaClinica+
                ", Raza="+Raza+
                '}';
    }
}
