package UI;

import Logica_de_Negocio.*;
import Persistencia_de_Datos.*;

import javax.swing.*;
import java.time.LocalDate;

public class InterfazGrafica extends JFrame {
    private JButton btnAbrirArchivo;
    private JButton btnNuevoExperimento;
    private JButton btnAgregarPoblacion;
    private JButton btnVisualizarPoblaciones;
    private JButton btnBorrarPoblacion;
    private JButton btnVerDetalle;
    private JButton btnSimulacionMontecarlo;
    private Experimento experimento;

    private PatronDeComida seleccionarPatronDeComida(int comidaInicial, int comidaFinal, int duracion) {
        String[] opciones = {"Constante", "Incremento lineal", "Alterno", "Decremento e incremento"};
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un patrón de comida:", "Patrón de Comida", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        switch (seleccion) {
            case "Constante":
                return new PatronAlimentacionConstante(comidaInicial);
            case "Incremento lineal":
                return new PatronAlimentacionIncremento(comidaInicial, comidaFinal, duracion);
            case "Alterno":
                return new PatronAlimentacionAlterno(comidaInicial);
            case "Decremento e incremento":
                return new PatronAlimentacionDecrementoIncremento(comidaInicial, comidaFinal, duracion);
            default:
                throw new IllegalStateException("Patrón de comida no valido: " + seleccion);
        }
    }

    private Poblacion recogerDatosPoblacion() {
        Poblacion poblacion = new Poblacion();
        String nombrePoblacion = JOptionPane.showInputDialog("Introduce el nombre de la población: ");
        String fechaInicio = JOptionPane.showInputDialog("Introduce la fecha de inicio de la población (DD/MM/AAAA): ");
        String fechaFin = JOptionPane.showInputDialog("Introduce la fecha de fin de la población (DD/MM/AAAA): ");
        String numBacterias = JOptionPane.showInputDialog("Introduce el número de bacterias de la población: ");
        String temperatura = JOptionPane.showInputDialog("Introduce la temperatura de la población: ");
        String luminosidad = JOptionPane.showInputDialog("Introduce la luminosidad de la población (Alta, Media, Baja): ");
        String comidaInicial = JOptionPane.showInputDialog("Introduce la cantidad inicial de comida en microgramos (debe ser menor a 300,000): ");
        String diaIncrementoComida = JOptionPane.showInputDialog("Introduce el día hasta el cual se debe incrementar la cantidad de comida: ");

        String comidaDiaIncrementoStr;
        int comidaDiaIncremento;
        do {
            comidaDiaIncrementoStr = JOptionPane.showInputDialog("Introduce el incremento de comida diario en microgramos: ");
            try {
                comidaDiaIncremento = Integer.parseInt(comidaDiaIncrementoStr);
                if (comidaDiaIncremento > 300000) {
                    JOptionPane.showMessageDialog(null, "El incremento de comida diario no debe ser mayor a 300,000. Inténtalo de nuevo. ");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, introduce un número entero válido. Inténtalo de nuevo. ");
                comidaDiaIncremento = 300001;
            }
        } while (comidaDiaIncremento > 300000);
        String comidaFinal = JOptionPane.showInputDialog("Introduce la cantidad final de comida en microgramos (debe ser menor a 300,000): ");

        poblacion.setNombre(nombrePoblacion);
        poblacion.setFechaInicio(fechaInicio);
        poblacion.setFechaFin(fechaFin);
        poblacion.setNumBacteriasIniciales(Integer.parseInt(numBacterias));
        poblacion.setTemperatura(Double.parseDouble(temperatura));
        poblacion.setLuminosidad(luminosidad);
        poblacion.setComidaInicial(Integer.parseInt(comidaInicial));
        poblacion.setDiaIncrementoComida(Integer.parseInt(diaIncrementoComida));
        poblacion.setComidaDiaIncremento(comidaDiaIncremento);
        poblacion.setComidaFinal(Integer.parseInt(comidaFinal));

        // Seleccionar y asignar el patrón de comida
        PatronDeComida patronDeComida = seleccionarPatronDeComida(Integer.parseInt(comidaInicial), Integer.parseInt(comidaFinal), 30);
        poblacion.setPatronDeComida(patronDeComida);

        return poblacion;
    }

    public InterfazGrafica() {
        initComponents();
        setLayout(null);
        setSize(200, 350); // Ajustar el tamaño de la ventana para acomodar el nuevo botón
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestión de cultivos de bacterias. ");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        btnAbrirArchivo = new JButton("<html>Abrir archivo<br>de un experimento</html>");
        btnNuevoExperimento = new JButton("Nuevo experimento");
        btnAgregarPoblacion = new JButton("Agregar población");
        btnVisualizarPoblaciones = new JButton("<html>Visualizar nombres<br>poblaciones</html>");
        btnBorrarPoblacion = new JButton("Borrar población");
        btnVerDetalle = new JButton("<html>Información detallada<br>de una población</html>");
        btnSimulacionMontecarlo = new JButton("Simulación Montecarlo");

        btnAbrirArchivo.setBounds(20, 20, 150, 35);
        btnNuevoExperimento.setBounds(20, 60, 150, 30);
        btnAgregarPoblacion.setBounds(20, 100, 150, 30);
        btnVisualizarPoblaciones.setBounds(20, 140, 150, 35);
        btnBorrarPoblacion.setBounds(20, 180, 150, 30);
        btnVerDetalle.setBounds(20, 220, 150, 35);
        btnSimulacionMontecarlo.setBounds(20, 260, 150, 35);

        add(btnAbrirArchivo);
        add(btnNuevoExperimento);
        add(btnAgregarPoblacion);
        add(btnVisualizarPoblaciones);
        add(btnBorrarPoblacion);
        add(btnVerDetalle);
        add(btnSimulacionMontecarlo);

        btnAbrirArchivo.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            if (nombresExperimentos.length == 0) {
                JOptionPane.showMessageDialog(null, "No hay experimentos guardados en esta carpeta.");
                return;
            }
            String nombreArchivo = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir archivo de experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                System.out.println("Archivo seleccionado: " + nombreArchivo);
                experimento = GestorDatos.cargarExperimento(nombreArchivo);
                if (experimento != null) {
                    JOptionPane.showMessageDialog(null, "Experimento cargado correctamente.");
                    new ExperimentoDialogo(experimento).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el experimento.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún experimento.");
            }
        });

        btnNuevoExperimento.addActionListener(e -> {
            String nombreExperimento = JOptionPane.showInputDialog("Introduce el nombre del experimento:");
            if (nombreExperimento != null && !nombreExperimento.trim().isEmpty()) {
                experimento = new Experimento(nombreExperimento, LocalDate.now(), 30, 300000, new PatronAlimentacionConstante(1000));
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres añadir una población al experimento?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    String numPoblacionesStr = JOptionPane.showInputDialog("¿Cuántas poblaciones quieres agregar?");
                    if (numPoblacionesStr != null && !numPoblacionesStr.trim().isEmpty()) {
                        try {
                            int numPoblaciones = Integer.parseInt(numPoblacionesStr);
                            for (int i = 0; i < numPoblaciones; i++) {
                                Poblacion poblacion = recogerDatosPoblacion();
                                experimento.getPoblaciones().add(poblacion);
                            }
                            JOptionPane.showMessageDialog(null, "Nuevo experimento creado.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Error: se esperaba un número.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: se esperaba un número.");
                    }
                }
                String nombreArchivo = experimento.getNombre() + ".txt";
                GestorDatos.guardarExperimentos(nombreArchivo, experimento);
                JOptionPane.showMessageDialog(null, "Experimento guardado.");
            } else {
                JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de experimento.");
            }
        });

        btnAgregarPoblacion.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            String nombreArchivo = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir Experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                experimento = GestorDatos.cargarExperimento(nombreArchivo);
                if (experimento != null) {
                    Poblacion poblacion = recogerDatosPoblacion();
                    experimento.getPoblaciones().add(poblacion);
                    GestorDatos.guardarExperimentos(nombreArchivo, experimento);
                    JOptionPane.showMessageDialog(null, "Población agregada al experimento.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el experimento.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de archivo.");
            }
        });

        btnVisualizarPoblaciones.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            String nombreArchivo = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir Experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                experimento = GestorDatos.cargarExperimento(nombreArchivo);
                if (experimento != null) {
                    if (!experimento.getPoblaciones().isEmpty()) {
                        StringBuilder nombresPoblaciones = new StringBuilder("Nombres de las poblaciones:\n");
                        for (Poblacion poblacion : experimento.getPoblaciones()) {
                            nombresPoblaciones.append(poblacion.getNombre()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, nombresPoblaciones.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay poblaciones para visualizar.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el experimento.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de archivo.");
            }
        });

        btnBorrarPoblacion.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            String nombreArchivo = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir Experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                experimento = GestorDatos.cargarExperimento(nombreArchivo);
                if (experimento != null) {
                    if (!experimento.getPoblaciones().isEmpty()) {
                        String[] nombresPoblaciones = experimento.getPoblaciones().stream().map(Poblacion::getNombre).toArray(String[]::new);
                        String nombrePoblacion = (String) JOptionPane.showInputDialog(null, "Selecciona una población para eliminar:", "Eliminar Población", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);
                        if (nombrePoblacion != null && !nombrePoblacion.trim().isEmpty()) {
                            experimento.getPoblaciones().removeIf(poblacion -> poblacion.getNombre().equals(nombrePoblacion));
                            GestorDatos.guardarExperimentos(nombreArchivo, experimento);
                            JOptionPane.showMessageDialog(null, "Población eliminada del experimento.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de población.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay poblaciones para eliminar.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el experimento.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de archivo.");
            }
        });

        btnVerDetalle.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            String nombreExperimento = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir Experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreExperimento != null) {
                Experimento experimento = GestorDatos.cargarExperimento(nombreExperimento);
                if (experimento != null) {
                    String[] nombresPoblaciones = experimento.getPoblaciones().stream().map(Poblacion::getNombre).toArray(String[]::new);
                    String nombrePoblacion = (String) JOptionPane.showInputDialog(null, "Selecciona una población:", "Seleccionar Población", JOptionPane.QUESTION_MESSAGE, null, nombresPoblaciones, nombresPoblaciones[0]);
                    if (nombrePoblacion != null) {
                        Poblacion poblacion = experimento.getPoblaciones().stream()
                                .filter(p -> p.getNombre().equals(nombrePoblacion))
                                .findFirst()
                                .orElse(null);
                        if (poblacion != null) {
                            new PoblacionDialogo(poblacion).setVisible(true);
                            new ComidaDialogo(poblacion).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró la población", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el experimento", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSimulacionMontecarlo.addActionListener(e -> {
            String[] nombresExperimentos = GestorDatos.obtenerNombresExperimentos();
            String nombreArchivo = (String) JOptionPane.showInputDialog(null, "Selecciona un experimento:", "Abrir Experimento", JOptionPane.QUESTION_MESSAGE, null, nombresExperimentos, nombresExperimentos[0]);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                experimento = GestorDatos.cargarExperimento(nombreArchivo);
                if (experimento != null) {
                    String iteracionesStr = JOptionPane.showInputDialog("Introduce el número de iteraciones para la simulación:");
                    try {
                        int iteraciones = Integer.parseInt(iteracionesStr);
                        double[] resultados = SimulacionMontecarlo.ejecutarSimulacion(experimento, iteraciones);
                        mostrarResultadosSimulacion(resultados);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Error: se esperaba un número.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar el experimento.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: se esperaba un nombre de archivo.");
            }
        });
    }

    private void mostrarResultadosSimulacion(double[] resultados) {
        double suma = 0;
        for (double resultado : resultados) {
            suma += resultado;
        }
        double media = suma / resultados.length;

        double varianza = 0;
        for (double resultado : resultados) {
            varianza += Math.pow(resultado - media, 2);
        }
        varianza /= resultados.length;

        double desviacionEstandar = Math.sqrt(varianza);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Resultados de la Simulación de Montecarlo\n");
        mensaje.append("Número de iteraciones: ").append(resultados.length).append("\n");
        mensaje.append("Media de la población total: ").append(media).append("\n");
        mensaje.append("Desviación estándar: ").append(desviacionEstandar).append("\n");

        JOptionPane.showMessageDialog(this, mensaje.toString(), "Resultados Simulación Montecarlo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}
