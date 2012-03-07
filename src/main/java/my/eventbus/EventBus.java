package my.eventbus;

/**
 * Dispatches {@link my.eventbus.Event}s to registered handlers
 */
public interface EventBus {

    /**
     * Register a handler for events of type {@code T}
     * 
     * @param handler
     *            the handler to register
     * @return a {@link my.eventbus.HandlerRegistration} that can be used to
     *         unregister the handler
     */
    public <T extends Event> HandlerRegistration addHandler(Handler<T> handler);

    /**
     * Register a handler for events of type {@code T} dispatched from source
     * {@code source}
     * 
     * @param handler
     *            the handler to register
     * @param source
     *            the source that fired the event
     * @return a {@link my.eventbus.HandlerRegistration} that can be used to
     *         unregister the handler
     */
    public <T extends Event> HandlerRegistration addHandlerToSource(Handler<T> handler, Object source);

    /**
     * Fire an event of type {@code T}
     * 
     * @param event
     *            the event to fire
     */
    public <T extends Event> void fireEvent(T event);

    /**
     * Fire an event of type {@code T} from source {@code source}
     * 
     * @param event
     *            the event to fire
     * @param source
     *            the source of the event
     */
    public <T extends Event> void fireEventFromSource(T event, Object source);

}
