package my.eventbus;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Two threads that runs in parallel and send events to each other
 * 
 * @author david
 * 
 */
public class TestCount {

    ExecutorService executor = Executors.newCachedThreadPool();

    @Test
    public void testCount() throws Exception {
        EventBus eventBus = new SimpleEventBus();

        Integer countTo = new Integer(10);

        Future<Integer> counter1 = executor.submit(new Counter(countTo, eventBus));
        Future<Integer> counter2 = executor.submit(new Counter(countTo, eventBus));

        assertEquals(countTo, counter1.get());
        assertEquals(countTo, counter2.get());
    }

    /**
     * Simple event
     */
    private class CountEvent implements Event {
    }

    /**
     * Counter thread that fires and handles CountEvent
     */
    private class Counter implements Callable<Integer>, Handler<CountEvent> {

        private int countTo;
        private volatile int count = 0;

        private EventBus eventBus;
        private HandlerRegistration handlerRegistration;

        public Counter(int countTo, EventBus eventBus) {
            this.countTo = countTo;
            this.eventBus = eventBus;
        }

        @Override
        public Integer call() {
            handlerRegistration = eventBus.addHandler(this);
            Random rand = new Random();
            for (int i = 0; i < countTo; ++i) {
                eventBus.fireEventFromSource(new CountEvent(), this);
                sleep(rand.nextInt(500));
            }
            while (count < countTo) {
                sleep(100);
            }
            handlerRegistration.removeRegistration();
            return count;
        }

        @Override
        public Class<CountEvent> getType() {
            return CountEvent.class;
        }

        @Override
        public void handleEvent(CountEvent event, Object source) {
            if (source != this) {
                ++count;
            }
        }

        private void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // Oops
            }
        }
    }

}
