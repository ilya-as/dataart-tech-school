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
        double result = 0.0;
        double numerator = 1D;
        double denominator = 1D;
        for (int currentAdded = 2; currentAdded <= addendNumber; currentAdded++) {
            numerator = numerator * x;
            denominator *= (currentAdded - 1);
            if ((currentAdded - 1) % 4 == 1) {
                result += numerator / denominator;
            }
            if ((currentAdded - 1) % 4 == 3) {
                result -= numerator / denominator;
            }
        }
        return result;
    }
}
