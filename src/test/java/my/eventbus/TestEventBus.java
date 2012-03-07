package my.eventbus;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class TestEventBus {

    EventBus eventBus;
    Handler<Event1> handler1;
    Handler<Event2> handler2;
    Event1 event1;
    Event2 event2;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        eventBus = new SimpleEventBus();
        handler1 = (Handler<Event1>) mock(Handler.class);
        handler2 = (Handler<Event2>) mock(Handler.class);
        when(handler1.getType()).thenReturn(Event1.class);
        when(handler2.getType()).thenReturn(Event2.class);
        event1 = new Event1();
        event2 = new Event2();
    }

    @Test
    public void testAddHandler() {
        eventBus.addHandler(handler1);
    }

    @Test
    public void testAddHandlerToSource() {
        eventBus.addHandlerToSource(handler1, this);
    }

    @Test
    public void testAddHandlerToSourceNull() {
        eventBus.addHandlerToSource(handler1, null);
    }

    @Test
    public void testFireEvent() {
        eventBus.addHandler(handler1);
        eventBus.fireEvent(event1);
        verify(handler1).handleEvent(event1, null);
    }

    @Test
    public void testFireEventFromSource() {
        eventBus.addHandlerToSource(handler1, this);
        eventBus.fireEventFromSource(event1, this);
        verify(handler1).handleEvent(event1, this);
    }

    @Test
    public void testFireEventMulipleTimes() {
        eventBus.addHandler(handler1);
        for (int i = 0; i < 10; ++i) {
            eventBus.fireEvent(event1);
        }
        verify(handler1, times(10)).handleEvent(event1, null);
    }

    @Test
    public void testFireEventNoHandlers() {
        eventBus.fireEvent(event1);
    }

    @Test
    public void testFireEventNotHandled() {
        eventBus.addHandler(handler1);
        eventBus.fireEvent(event2);
        verify(handler1, never()).handleEvent(any(Event1.class), any());
    }

    @Test
    public void testFireEventFromSourceNotHandled() {
        eventBus.addHandlerToSource(handler1, this);
        eventBus.fireEventFromSource(event2, this);
        verify(handler1, never()).handleEvent(any(Event1.class), any());
    }

    @Test
    public void testFireEventFromSourceNotHandledWrongSource() {
        eventBus.addHandlerToSource(handler1, this);
        eventBus.fireEventFromSource(event1, System.class);
        verify(handler1, never()).handleEvent(any(Event1.class), any());
    }

    @Test
    public void testAddHandlerFireEventFromSource() {
        eventBus.addHandler(handler1);
        eventBus.fireEventFromSource(event1, this);
        verify(handler1).handleEvent(event1, this);
    }

    private class Event1 implements Event {
    }

    private class Event2 implements Event {
    }

}
