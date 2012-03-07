package my.eventbus;

public interface Handler<T extends Event> {

    public Class<T> getType();

    public void handleEvent(T event);

}
