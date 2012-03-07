package my.eventbus;

import org.junit.Before;
import org.junit.Test;

public class TestEventBus {

    EventBus bus;

    @Before
    public void setUp() {
        bus = new SimpleEventBus();
    }

    @Test
    public void testEventBus() {
        HandlerRegistration reg = bus.addHandler(new MyEventHandler());
        bus.fireEvent(new MyEvent());
        reg.removeRegistration();
        bus.fireEvent(new MyEvent());
    }

    private class MyEvent implements Event {
    }

    private class MyEventHandler implements Handler<MyEvent> {

        @Override
        public Class<MyEvent> getType() {
            return MyEvent.class;
        }

        @Override
        public void handleEvent(MyEvent event) {
            System.out.println("Handle " + event);
        }

    }

}
