package com.example.mascota.ui.vervacunacion;

public class Vacunacion {

    private String Fecha;
    private String ImagenPrueba;
    private String Vacuna;

    public Vacunacion() {

    }

    public Vacunacion(String Fecha, String ImagenPrueba, String Vacuna) {
        this.Fecha = Fecha;
        this.ImagenPrueba = ImagenPrueba;
        this.Vacuna = Vacuna;

    }


    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getImagenPrueba() {
        return ImagenPrueba;
    }

    public void setImagenPrueba(String ImagenPrueba) {
        this.ImagenPrueba = ImagenPrueba;

    }

    public String getVacuna() {
        return Vacuna;
    }

    public void setVacuna(String Vacuna) {
        this.Vacuna = Vacuna;
    }


    @Override
    public String toString() {

        return "Desparasitacion{" +
                "Fecha='" + Fecha + '\'' +
                ", ImagenPrueba=" + ImagenPrueba +
                ", Vacuna=" + Vacuna +
                '}';

    }

}
