package Logica_de_Negocio;

public class PatronAlimentacionConstante implements PatronDeComida {
    private final int dailyAmount;

    public PatronAlimentacionConstante(int dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    @Override
    public int getFoodAmount(int day) {
        return dailyAmount;
    }
}
