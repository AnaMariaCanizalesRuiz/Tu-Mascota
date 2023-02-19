package com.example.mascota.ui.home;

public class IdMascota {

    private String Mascota;

    public IdMascota(){

    }

    public IdMascota(String Mascota){

        this.Mascota = Mascota;

    }

    public String getMascota(){
        return Mascota;
    }

    public void setMascota(String Mascota){
        this.Mascota = Mascota;
    }


    @Override
    public String toString(){

        return "Mascota{"+Mascota+'}';

    }
}
