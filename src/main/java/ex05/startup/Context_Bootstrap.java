package ex05.startup;

import ex05.connector.http.DefaultConnector;
import ex05.core.DefaultContainer.DefaultContext;
import ex05.core.LifecycleListener.SimpleContextLifeCycleListener;
import ex05.core.LifecycleListener.SimpleLoaderLifeCycleListener;
import ex05.core.values.ClientIPLoggerValue;
import ex05.core.values.HeaderLoggerValue;
import ex05.core.values.basicValues.SimpleContextValue;
import org.apache.catalina.LifecycleException;

public class Context_Bootstrap {
    public static void main(String[] args) {
        DefaultContext defaultContext = new DefaultContext();
        //value
        ClientIPLoggerValue clientIPLoggerValue = new ClientIPLoggerValue();
        HeaderLoggerValue headerLoggerValue = new HeaderLoggerValue();
        defaultContext.addValue(clientIPLoggerValue);
        defaultContext.addValue(headerLoggerValue);
        //basic value
        SimpleContextValue simpleContextValue = new SimpleContextValue(defaultContext);
        defaultContext.setBasicValue(simpleContextValue);

        //add EventListener
        SimpleContextLifeCycleListener simpleContextLifeCycleListener=new SimpleContextLifeCycleListener();
        defaultContext.addLifecycleListener(simpleContextLifeCycleListener);
        SimpleLoaderLifeCycleListener simpleLoaderLifeCycleListener=new SimpleLoaderLifeCycleListener();
        defaultContext.addLifecycleListener(simpleLoaderLifeCycleListener);

        //start Container
        try {
            defaultContext.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }


        DefaultConnector connector = new DefaultConnector();
        connector.setContainer(defaultContext);
        Thread thread = new Thread(connector);
        thread.start();
    }
}
