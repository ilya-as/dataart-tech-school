public class SineCalculator {

    public double calculateExponent(int x, int addendNumber) {
        double result = 1D;
        double numerator = 1D;
        double denominator = 1D;
        for (int currentAdded = 2; currentAdded <= addendNumber; currentAdded++) {
            numerator = numerator * x;
            denominator *= (currentAdded - 1);
            result += numerator / denominator;
        }
        return result;
    }

    public double calculateSine(double x, int addendNumber) {
        double result = x;
        double numerator = x;
        double denominator = 1D;
        for (int currentAdded = 2; currentAdded <= addendNumber; currentAdded++) {
            numerator = numerator * x * x;
            denominator *= (currentAdded * 2 - 2) * (currentAdded * 2 - 1);
            numerator *= -1;
            result += numerator / denominator;
        }
        return result;
    }
}
