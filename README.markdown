## A simple java event bus

http://davvo.github.com/eventbus/

**Implement Event**

    public class MyEvent implements Event {
        ...
    }

**Implement Handler**
    
    public class MyHandler implements Handler<MyEvent> {
  
        @Override
        public void handleEvent(MyEvent event, Object source) {
            System.out.println("Handle " + event);
        }
        
        ...
        
    }

**Start firing events**

    EventBus eventBus = new SimpleEventBus();
    eventBus.addHandler(new MyHandler());
    ...
    eventBus.fireEvent(new MyEvent());
