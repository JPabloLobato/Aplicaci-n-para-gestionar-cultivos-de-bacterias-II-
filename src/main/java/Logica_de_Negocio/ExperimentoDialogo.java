package Logica_de_Negocio;

import Persistencia_de_Datos.GestorDatos;

import javax.swing.*;
import java.awt.*;

public class ExperimentoDialogo extends JDialog {
    private final Experimento experimento;
    private JTextField txtNombre;
    private JTextField txtNombrePoblacion;
    private JTextField txtFechaInicioPoblacion;
    private JTextField txtFechaFinPoblacion;
    private JTextField txtNumBacteriasInicialesPoblacion;
    private JTextField txtTemperaturaPoblacion;
    private JTextField txtLuminosidadPoblacion;
    private JTextField txtComidaInicialPoblacion;
    private JTextField txtDiaIncrementoComidaPoblacion;
    private JTextField txtComidaDiaIncrementoPoblacion;
    private JTextField txtComidaFinalPoblacion;
    private JComboBox<String> cmbPatronComida;

    public ExperimentoDialogo(Experimento experimento) {
        this.experimento = experimento;
        setTitle("Información del experimento");
        setSize(400, 600);
        setLayout(new GridLayout(0, 2));
        setLocationRelativeTo(null);
        setModal(true);
        DialogoExperimentoCompleto();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnGuardarComo = new JButton("Guardar como");

        btnGuardar.addActionListener(e -> guardarExperimento());

        btnGuardarComo.addActionListener(e -> guardarComo());

        add(btnGuardar);
        add(btnGuardarComo);
    }

    private void DialogoExperimentoCompleto() {
        add(new JLabel("Nombre del experimento:"));
        txtNombre = new JTextField(experimento.getNombre());
        add(txtNombre);
        for (Poblacion poblacion : experimento.getPoblaciones()) {
            add(new JLabel("Nombre de la población:"));
            txtNombrePoblacion = new JTextField(poblacion.getNombre());
            add(txtNombrePoblacion);
            add(new JLabel("Fecha de inicio de la población:"));
            txtFechaInicioPoblacion = new JTextField(poblacion.getFechaInicio());
            add(txtFechaInicioPoblacion);
            add(new JLabel("Fecha de fin de la población:"));
            txtFechaFinPoblacion = new JTextField(poblacion.getFechaFin());
            add(txtFechaFinPoblacion);
            add(new JLabel("Número de bacterias iniciales:"));
            txtNumBacteriasInicialesPoblacion = new JTextField(String.valueOf(poblacion.getNumBacteriasIniciales()));
            add(txtNumBacteriasInicialesPoblacion);
            add(new JLabel("Temperatura (°C):"));
            txtTemperaturaPoblacion = new JTextField(String.valueOf(poblacion.getTemperatura()));
            add(txtTemperaturaPoblacion);
            add(new JLabel("Luminosidad:"));
            txtLuminosidadPoblacion = new JTextField(poblacion.getLuminosidad());
            add(txtLuminosidadPoblacion);
            add(new JLabel("Comida inicial (µg):"));
            txtComidaInicialPoblacion = new JTextField(String.valueOf(poblacion.getComidaInicial()));
            add(txtComidaInicialPoblacion);
            add(new JLabel("Día de incremento de comida:"));
            txtDiaIncrementoComidaPoblacion = new JTextField(String.valueOf(poblacion.getDiaIncrementoComida()));
            add(txtDiaIncrementoComidaPoblacion);
            add(new JLabel("Comida de incremento (µg):"));
            txtComidaDiaIncrementoPoblacion = new JTextField(String.valueOf(poblacion.getComidaDiaIncremento()));
            add(txtComidaDiaIncrementoPoblacion);
            add(new JLabel("Comida final (µg):"));
            txtComidaFinalPoblacion = new JTextField(String.valueOf(poblacion.getComidaFinal()));
            add(txtComidaFinalPoblacion);
        }
        add(new JLabel("Patrón de comida:"));
        cmbPatronComida = new JComboBox<>(new String[]{"Constante", "Incremento lineal", "Alterno", "Decremento e incremento"});
        add(cmbPatronComida);
    }

    private void guardarExperimento() {
        experimento.setNombre(txtNombre.getText());
        for (Poblacion poblacion : experimento.getPoblaciones()) {
            poblacion.setNombre(txtNombrePoblacion.getText());
            poblacion.setFechaInicio(txtFechaInicioPoblacion.getText());
            poblacion.setFechaFin(txtFechaFinPoblacion.getText());
            poblacion.setNumBacteriasIniciales(Integer.parseInt(txtNumBacteriasInicialesPoblacion.getText()));
            poblacion.setTemperatura(Double.parseDouble(txtTemperaturaPoblacion.getText()));
            poblacion.setLuminosidad(txtLuminosidadPoblacion.getText());
            int comidaInicial = Integer.parseInt(txtComidaInicialPoblacion.getText());
            int comidaDiaIncremento = Integer.parseInt(txtComidaDiaIncrementoPoblacion.getText());
            int comidaFinal = Integer.parseInt(txtComidaFinalPoblacion.getText());

            if (comidaInicial > 300000 || comidaDiaIncremento > 300000 || comidaFinal > 300000) {
                JOptionPane.showMessageDialog(this, "La comida no debe ser mayor a 300,000 µg.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            poblacion.setComidaInicial(comidaInicial);
            poblacion.setDiaIncrementoComida(Integer.parseInt(txtDiaIncrementoComidaPoblacion.getText()));
            poblacion.setComidaDiaIncremento(comidaDiaIncremento);
            poblacion.setComidaFinal(comidaFinal);

            String patronSeleccionado = (String) cmbPatronComida.getSelectedItem();
            if (patronSeleccionado == null) {
                throw new IllegalStateException("No se ha seleccionado un patrón de comida.");
            }

            PatronDeComida patronDeComida;

            switch (patronSeleccionado) {
                case "Constante":
                    patronDeComida = new PatronAlimentacionConstante(comidaInicial);
                    break;
                case "Incremento lineal":
                    patronDeComida = new PatronAlimentacionIncremento(comidaInicial, comidaFinal, experimento.getDuracion());
                    break;
                case "Alterno":
                    patronDeComida = new PatronAlimentacionAlterno(comidaInicial);
                    break;
                case "Decremento e incremento":
                    patronDeComida = new PatronAlimentacionDecrementoIncremento(comidaInicial, comidaFinal, experimento.getDuracion());
                    break;
                default:
                    throw new IllegalStateException("Patrón de comida no valido: " + patronSeleccionado);
            }
            poblacion.setPatronDeComida(patronDeComida);

            GestorDatos.guardarExperimentos(experimento.getNombre() + ".txt", experimento);
            JOptionPane.showMessageDialog(null, "Experimento guardado.");
        }
    }

    private void guardarComo() {
        String nuevoNombre = JOptionPane.showInputDialog(this, "Introduce el nuevo nombre para el experimento:", experimento.getNombre());
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            experimento.setNombre(nuevoNombre);
            guardarExperimento();
        } else {
            JOptionPane.showMessageDialog(this, "Error: se esperaba un nombre de experimento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
