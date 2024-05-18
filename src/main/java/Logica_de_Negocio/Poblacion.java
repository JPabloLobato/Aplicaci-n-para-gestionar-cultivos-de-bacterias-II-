package Logica_de_Negocio;

public class Poblacion {
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private int numBacteriasIniciales;
    private double temperatura;
    private String luminosidad;
    private int comidaInicial;
    private int diaIncrementoComida;
    private int comidaDiaIncremento;
    private int comidaFinal;
    private PatronDeComida patronDeComida;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNumBacteriasIniciales() {
        return numBacteriasIniciales;
    }

    public void setNumBacteriasIniciales(int numBacteriasIniciales) {
        this.numBacteriasIniciales = numBacteriasIniciales;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public int getComidaInicial() {
        return comidaInicial;
    }

    public void setComidaInicial(int comidaInicial) {
        this.comidaInicial = comidaInicial;
    }

    public int getDiaIncrementoComida() {
        return diaIncrementoComida;
    }

    public void setDiaIncrementoComida(int diaIncrementoComida) {
        this.diaIncrementoComida = diaIncrementoComida;
    }

    public int getComidaDiaIncremento() {
        return comidaDiaIncremento;
    }

    public void setComidaDiaIncremento(int comidaDiaIncremento) {
        this.comidaDiaIncremento = comidaDiaIncremento;
    }

    public int getComidaFinal() {
        return comidaFinal;
    }

    public void setComidaFinal(int comidaFinal) {
        this.comidaFinal = comidaFinal;
    }

    public PatronDeComida getPatronDeComida() {
        return patronDeComida;
    }

    public void setPatronDeComida(PatronDeComida patronDeComida) {
        this.patronDeComida = patronDeComida;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Fecha de inicio: " + fechaInicio + "\n" +
                "Fecha de fin: " + fechaFin + "\n" +
                "Número de bacterias iniciales: " + numBacteriasIniciales + "\n" +
                "Temperatura: " + temperatura + "\n" +
                "Luminosidad: " + luminosidad + "\n" +
                "Comida inicial: " + comidaInicial + "\n" +
                "Día de incremento de comida: " + diaIncrementoComida + "\n" +
                "Comida de incremento: " + comidaDiaIncremento + "\n" +
                "Comida final: " + comidaFinal + "\n" +
                "Patrón de comida: " + patronDeComida.getClass().getSimpleName() + "\n";
    }
}
