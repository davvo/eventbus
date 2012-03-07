package my.eventbus;

/**
 * A handler for events of type {@code T}
 * 
 * @param <T>
 *            the type of event
 */
public interface Handler<T extends Event> {

    /**
     * @return the event type
     */
    public Class<T> getType();

    /**
     * Handle an event of type {@code T}, fired by source {@code source}.
     * 
     * @param event
     *            the event to handle
     * @param source
     *            the source that fired the event. Can be null.
     */
    public void handleEvent(T event, Object source);

}
