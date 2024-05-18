package Logica_de_Negocio;

public class PatronAlimentacionIncremento implements PatronDeComida {
    private final int initialAmount;
    private final int finalAmount;
    private final int duration;

    public PatronAlimentacionIncremento(int initialAmount, int finalAmount, int duration) {
        this.initialAmount = initialAmount;
        this.finalAmount = finalAmount;
        this.duration = duration;
    }

    @Override
    public int getFoodAmount(int day) {
        return initialAmount + (finalAmount - initialAmount) * day / duration;
    }
}

