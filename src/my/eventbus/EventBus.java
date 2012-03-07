package my.eventbus;

public interface EventBus {

    public <T extends Event> HandlerRegistration addHandler(Handler<T> handler);

    public <T extends Event> HandlerRegistration addHandlerToSource(Handler<T> handler, Object source);

    public <T extends Event> void fireEvent(T event);

    public <T extends Event> void fireEventFromSource(T event, Object source);

}
