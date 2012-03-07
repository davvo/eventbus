package my.eventbus;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A simple {@link my.eventbus.EventBus} implementation
 */
public class SimpleEventBus implements EventBus {

    private ConcurrentMap<Class<? extends Event>, Set<HandlerWrapper>> handlers = new ConcurrentHashMap<Class<? extends Event>, Set<HandlerWrapper>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Event> HandlerRegistration addHandler(Handler<T> handler) {
        return addHandlerToSource(handler, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Event> HandlerRegistration addHandlerToSource(Handler<T> handler, Object source) {
        HandlerWrapper wrapper = new HandlerWrapper(handler, source);

        if (!handlers.containsKey(handler.getType())) {
            Set<HandlerWrapper> wrappers = Collections.newSetFromMap(new ConcurrentHashMap<HandlerWrapper, Boolean>());
            handlers.putIfAbsent(handler.getType(), wrappers);
        }

        Set<HandlerWrapper> wrappers = handlers.get(handler.getType());
        wrappers.add(wrapper);
        return wrapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Event> void fireEvent(T event) {
        fireEventFromSource(event, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event> void fireEventFromSource(T event, Object source) {
        if (handlers.containsKey(event.getClass())) {
            for (HandlerWrapper wrapper : handlers.get(event.getClass())) {
                if (source == null || wrapper.source == null || source == wrapper.source) {
                    ((Handler<T>) wrapper.handler).handleEvent(event, source);
                }
            }
        }
    }

    private class HandlerWrapper implements HandlerRegistration {

        Handler<? extends Event> handler;
        Object source;

        public HandlerWrapper(Handler<? extends Event> handler, Object source) {
            this.handler = handler;
            this.source = source;
        }

        @Override
        public void removeRegistration() {
            if (handlers.containsKey(handler.getType())) {
                handlers.get(handler.getType()).remove(this);
            }
        }

    }

}
