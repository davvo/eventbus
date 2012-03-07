package my.eventbus;

/**
 * A reference returned by {@link my.eventbus.EventBus#addHandler(Handler)} and
 * {@link my.eventbus.EventBus#addHandlerToSource(Handler, Object)} that can be
 * used to unregister the handler.
 */
public interface HandlerRegistration {

    /**
     * Unregister handler
     */
    public void removeRegistration();

}
