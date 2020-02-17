package recursion;

public class FibNumbers {

    public int countFib(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1 || i == 2) {
            return 1;
        }
        return countFib(i - 1) + countFib(i - 2);
    }
}
