package Logica_de_Negocio;

public class PatronAlimentacionDecrementoIncremento implements PatronDeComida {
    private final int initialAmount;
    private final int peakAmount;
    private final int duration;

    public PatronAlimentacionDecrementoIncremento(int initialAmount, int peakAmount, int duration) {
        this.initialAmount = initialAmount;
        this.peakAmount = peakAmount;
        this.duration = duration;
    }

    @Override
    public int getFoodAmount(int day) {
        int midPoint = duration / 2;
        if (day <= midPoint) {
            return initialAmount + (peakAmount - initialAmount) * day / midPoint;
        } else {
            return peakAmount - (peakAmount - initialAmount) * (day - midPoint) / (duration - midPoint);
        }
    }
}
