package treads;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleBlockingQueueTest {
    @Test
    public void simpleBlockingTest() {
        Queue<Integer> result = new LinkedList<>();
        Queue<Integer> expect = new LinkedList<>();
        SimpleBlockingQueue<Integer> simpleQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                expect.offer(i);
                simpleQueue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    result.offer(simpleQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(result, is(expect));
    }
}
