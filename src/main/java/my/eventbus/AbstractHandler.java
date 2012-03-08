package my.eventbus;

public abstract class AbstractHandler<T extends Event> implements Handler<T> {

    private Class<T> type;

    public AbstractHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

}
