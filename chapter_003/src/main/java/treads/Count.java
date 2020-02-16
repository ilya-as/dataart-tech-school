package treads;

/**
 * Потокобезопасный счетчик.
 * Потокобезопасность обеспечивается оператором synchronized. Этот оператор предваряет блок кода или метод,
 * который подлежит синхронизации.В метод с оператором synchronized иожет зайти только один поток и работать с ним
 * монопольно.Следующий поток заходит только по окончании выполнения метода предыдущим потоком.
 * Если мы уберем отсюда оператор synchronized,счетчик станет не потокобезопасным.
 */

public class Count {
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }
}
