package Logica_de_Negocio;

import java.util.Random;

public class SimulacionMontecarlo {
    private static final Random random = new Random();

    public static double[] ejecutarSimulacion(Experimento experimento, int iteraciones) {
        double[] resultados = new double[iteraciones];

        for (int i = 0; i < iteraciones; i++) {
            double poblacionTotal = 0;
            for (Poblacion poblacion : experimento.getPoblaciones()) {
                int dia = 0;
                int numBacterias = poblacion.getNumBacteriasIniciales();
                while (dia < experimento.getDuracion()) {
                    dia++;
                    numBacterias += numBacterias * (random.nextDouble() - 0.5); // Simula crecimiento aleatorio
                    int comidaDiaria = poblacion.getPatronDeComida().getFoodAmount(dia);
                    numBacterias += comidaDiaria / 1000; // Simula el efecto de la comida
                }
                poblacionTotal += numBacterias;
            }
            resultados[i] = poblacionTotal;
        }

        return resultados;
    }
}
