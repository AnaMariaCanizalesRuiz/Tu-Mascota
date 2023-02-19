package com.example.mascota.ui.verdesparasitacion;

public class Desparasitacion {

    private String Fecha;
    private String ImagenPrueba;
    private String Producto;

    public Desparasitacion(){

    }
    public Desparasitacion(String Fecha, String ImagenPrueba, String Producto){
        this.Fecha = Fecha;
        this.ImagenPrueba = ImagenPrueba;
        this.Producto = Producto;

    }


    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getImagenPrueba(){
        return ImagenPrueba;
    }

    public void setImagenPrueba(String ImagenPrueba){
        this.ImagenPrueba = ImagenPrueba;

    }

    public String getProducto(){
        return Producto;
    }

    public void setProducto(String Producto){
        this.Producto = Producto;
    }


    @Override
    public String toString(){

        return "Desparasitacion{" +
                "Fecha='" + Fecha + '\'' +
                ", ImagenPrueba=" + ImagenPrueba +
                ", Producto=" + Producto +
                '}';

    }









}
