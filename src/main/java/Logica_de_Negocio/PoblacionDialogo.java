package Logica_de_Negocio;

import javax.swing.*;
import java.awt.*;

public class PoblacionDialogo extends JDialog {
    private final Poblacion poblacion;

    public PoblacionDialogo(Poblacion poblacion) {
        this.poblacion = poblacion;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(poblacion.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
}
