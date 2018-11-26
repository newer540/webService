package ex05.core.LifecycleListener;

import ex05.core.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class SimpleContextLifeCycleListener  implements LifecycleListener{

    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {
        Lifecycle lifecycle = lifecycleEvent.getLifecycle();
        Context context= (Context) lifecycle;
        String type = lifecycleEvent.getType();
        if(Lifecycle.BEFORE_START_EVENT.equals(type)){
            System.out.println("Before Context.");
        }else if (Lifecycle.START_EVENT.equals(type)){
            System.out.println("starting context");
        }

    }
}
