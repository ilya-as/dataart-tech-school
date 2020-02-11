public class Main {
    private final int ARRAY_LENGTH = 10;

    public static void main(String[] args) {
        Main main = new Main();
        main.maxElement();
        main.swapLines(2, 4);
        main.swapColumns(2, 4);
        main.diagonalSum();
    }

    public void maxElement() {
        System.out.println("Поиск максимального элемента массива");
        int[] array = new int[ARRAY_LENGTH];
        int maxNumber = 0;
        int maxIndex = 0;

        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 31);
            System.out.print(array[i]);
            if (maxNumber <= array[i]) {
                maxNumber = array[i];
                maxIndex = i;
            }
        }
        System.out.println("Максимальный элемент: " + array[maxIndex]);
    }

    public void swapLines(int lineFrom, int lineTo) {
        System.out.println("Меняем местами строки в массиве");
        int[][] array = fillMatrixArray();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            int tmp = array[i][lineFrom];
            array[lineFrom][i] = array[lineTo][i];
            array[lineTo][i] = tmp;
        }
        System.out.println("После замены:");
        printMatrixArray(array);
    }

    public void swapColumns(int columnFrom, int columnTo) {
        System.out.println("Меняем местами колонки в массиве");
        int[][] array = fillMatrixArray();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            int tmp = array[i][columnFrom];
            array[i][columnFrom] = array[i][columnTo];
            array[i][columnTo] = tmp;
        }
        System.out.println("После замены:");
        printMatrixArray(array);
    }

    public void diagonalSum() {
        System.out.println("Посчитаем сумму элементов главной диагонали");
        int[][] array = fillMatrixArray();
        int sum = 0;
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            for (int j = 0; j < ARRAY_LENGTH; j++) {
                if (i > j) {
                    System.out.print(array[i][j] + " ");
                    sum += array[i][j];
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println("Сумма элементов под главной диагональю: " + sum);
    }

    private int[][] fillMatrixArray() {
        int[][] array = new int[ARRAY_LENGTH][ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            for (int j = 0; j < ARRAY_LENGTH; j++) {
                array[i][j] = (int) (Math.random() * 31);
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        return array;
    }

    private void printMatrixArray(int[][] array) {
        System.out.println("Исходный массив: ");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}

