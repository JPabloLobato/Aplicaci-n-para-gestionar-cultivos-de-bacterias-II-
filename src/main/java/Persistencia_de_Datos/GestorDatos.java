package Persistencia_de_Datos;

import Logica_de_Negocio.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDatos {
    private static final String EXPERIMENTOS_DIR = "./experimentos";

    public static void guardarExperimentos(String nombreArchivo, Experimento experimento) {
        File dir = new File(EXPERIMENTOS_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, nombreArchivo)))) {
            writer.write("Nombre: " + experimento.getNombre());
            writer.newLine();
            writer.write("Duración: " + experimento.getDuracion());
            writer.newLine();

            for (Poblacion poblacion : experimento.getPoblaciones()) {
                writer.write("Población: " + poblacion.getNombre());
                writer.newLine();
                writer.write("Fecha de inicio: " + poblacion.getFechaInicio());
                writer.newLine();
                writer.write("Fecha de fin: " + poblacion.getFechaFin());
                writer.newLine();
                writer.write("Número de bacterias iniciales: " + poblacion.getNumBacteriasIniciales());
                writer.newLine();
                writer.write("Temperatura: " + poblacion.getTemperatura());
                writer.newLine();
                writer.write("Luminosidad: " + poblacion.getLuminosidad());
                writer.newLine();
                writer.write("Comida inicial: " + poblacion.getComidaInicial() + " μg");
                writer.newLine();
                writer.write("Día incremento comida: " + poblacion.getDiaIncrementoComida());
                writer.newLine();
                writer.write("Comida día incremento: " + poblacion.getComidaDiaIncremento() + " μg");
                writer.newLine();
                writer.write("Comida final: " + poblacion.getComidaFinal() + " μg");
                writer.newLine();
                writer.write("Patron de comida: " + poblacion.getPatronDeComida().getClass().getSimpleName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Experimento cargarExperimento(String nombreArchivo) {
        File file = new File(EXPERIMENTOS_DIR, nombreArchivo + ".txt");
        Experimento experimento = new Experimento();

        if (!file.exists()) {
            System.out.println("Archivo no encontrado: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Poblacion poblacion = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Nombre: ")) {
                    experimento.setNombre(line.substring(8).trim());
                } else if (line.startsWith("Duración: ")) {
                    experimento.setDuracion(Integer.parseInt(line.substring(10).trim()));
                } else if (line.startsWith("Población: ")) {
                    if (poblacion != null) {
                        experimento.getPoblaciones().add(poblacion);
                    }
                    poblacion = new Poblacion();
                    poblacion.setNombre(line.substring(11).trim());
                } else if (line.startsWith("Fecha de inicio: ")) {
                    poblacion.setFechaInicio(line.substring(16).trim());
                } else if (line.startsWith("Fecha de fin: ")) {
                    poblacion.setFechaFin(line.substring(13).trim());
                } else if (line.startsWith("Número de bacterias iniciales: ")) {
                    poblacion.setNumBacteriasIniciales(Integer.parseInt(line.substring(30).trim()));
                } else if (line.startsWith("Temperatura: ")) {
                    poblacion.setTemperatura(Double.parseDouble(line.substring(13).trim()));
                } else if (line.startsWith("Luminosidad: ")) {
                    poblacion.setLuminosidad(line.substring(13).trim());
                } else if (line.startsWith("Comida inicial: ")) {
                    poblacion.setComidaInicial(Integer.parseInt(line.substring(15).trim()));
                } else if (line.startsWith("Dia incremento comida: ")) {
                    poblacion.setDiaIncrementoComida(Integer.parseInt(line.substring(22).trim()));
                } else if (line.startsWith("Comida día incremento: ")) {
                    poblacion.setComidaDiaIncremento(Integer.parseInt(line.substring(22).trim()));
                } else if (line.startsWith("Comida final: ")) {
                    poblacion.setComidaFinal(Integer.parseInt(line.substring(13).trim()));
                } else if (line.startsWith("Patron de comida: ")) {
                    String patron = line.substring(17).trim();
                    PatronDeComida patronDeComida;
                    switch (patron) {
                        case "PatronAlimentacionConstante":
                            patronDeComida = new PatronAlimentacionConstante(poblacion.getComidaInicial());
                            break;
                        case "PatronAlimentacionIncremento":
                            patronDeComida = new PatronAlimentacionIncremento(poblacion.getComidaInicial(), poblacion.getComidaFinal(), 30);
                            break;
                        case "PatronAlimentacionAlterno":
                            patronDeComida = new PatronAlimentacionAlterno(poblacion.getComidaInicial());
                            break;
                        case "PatronAlimentacionDecrementoIncremento":
                            patronDeComida = new PatronAlimentacionDecrementoIncremento(poblacion.getComidaInicial(), poblacion.getComidaFinal(), 30);
                            break;
                        default:
                            throw new IllegalStateException("Patrón de comida no valido: " + patron);
                    }
                    poblacion.setPatronDeComida(patronDeComida);
                }
            }
            if (poblacion != null) {
                experimento.getPoblaciones().add(poblacion);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Error de formato en el archivo: " + e.getMessage());
            return null;
        }
        return experimento;
    }

    public static String[] obtenerNombresExperimentos() {
        File dir = new File(EXPERIMENTOS_DIR);
        File[] archivos = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            List<String> nombres = new ArrayList<>();
            for (File archivo : archivos) {
                nombres.add(archivo.getName().replace(".txt", ""));
            }
            return nombres.toArray(new String[0]);
        }
        return new String[0];
    }
}
