package ex05.core.LifecycleListener;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class SimpleLoaderLifeCycleListener implements LifecycleListener {
    private String name="zhaodi";
    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {
        String type = lifecycleEvent.getType();
        if(Lifecycle.START_EVENT.equals(type))
            System.out.println(name+"start()");
    }
}
