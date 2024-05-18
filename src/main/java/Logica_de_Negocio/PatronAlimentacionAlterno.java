package Logica_de_Negocio;

public class PatronAlimentacionAlterno implements PatronDeComida {
    private final int dailyAmount;

    public PatronAlimentacionAlterno(int dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    @Override
    public int getFoodAmount(int day) {
        return (day % 2 == 0) ? dailyAmount : 0;
    }
}
