package Logica_de_Negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Experimento {
    private String nombre;
    private LocalDate fechaInicio;
    private int duracion;
    private int foodInMicrograms;
    private PatronDeComida foodPattern;
    private List<Poblacion> poblaciones;

    public Experimento(String nombre, LocalDate fechaInicio, int duracion, int foodInMicrograms, PatronDeComida foodPattern) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.foodInMicrograms = foodInMicrograms;
        this.foodPattern = foodPattern;
        this.poblaciones = new ArrayList<>();
    }

    // Constructor vacío
    public Experimento() {
        this.poblaciones = new ArrayList<>();
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getFoodInMicrograms() {
        return foodInMicrograms;
    }

    public void setFoodInMicrograms(int foodInMicrograms) {
        this.foodInMicrograms = foodInMicrograms;
    }

    public PatronDeComida getFoodPattern() {
        return foodPattern;
    }

    public void setFoodPattern(PatronDeComida foodPattern) {
        this.foodPattern = foodPattern;
    }

    public List<Poblacion> getPoblaciones() {
        return poblaciones;
    }

    public void setPoblaciones(List<Poblacion> poblaciones) {
        this.poblaciones = poblaciones;
    }
}
