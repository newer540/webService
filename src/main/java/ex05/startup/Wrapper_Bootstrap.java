package ex05.startup;

import ex05.connector.http.DefaultConnector;
import ex05.core.DefaultContainer.DefaultPipeline;
import ex05.core.DefaultContainer.DefaultWrapper;
import ex05.core.Value;
import ex05.core.loader.SimpleLoader;
import ex05.core.values.ClientIPLoggerValue;
import ex05.core.values.HeaderLoggerValue;
import ex05.core.values.basicValues.SimpleWrapperValue;
import org.apache.catalina.connector.Connector;


/**
 * 单线程&简化 版的Tomcat
 */

public class Wrapper_Bootstrap {
    public static void main(String[] args) {
        SimpleLoader simpleLoader = new SimpleLoader();
        DefaultWrapper wrapper=new DefaultWrapper("Ex04PrimitiveServlet",simpleLoader);
        //value
        Value clientIPLoggerValue = new ClientIPLoggerValue();
        Value headerLoggerValue = new HeaderLoggerValue();
        wrapper.addValue(clientIPLoggerValue);
        wrapper.addValue(headerLoggerValue);
        //basicValue
        SimpleWrapperValue simpleWrapperValue = new SimpleWrapperValue(wrapper);
        simpleWrapperValue.setContainer(wrapper);
        wrapper.setBasicValue(simpleWrapperValue);

        DefaultConnector connector=new DefaultConnector();
        connector.setContainer(wrapper);
        Thread thread = new Thread(connector);
        thread.start();
    }
}
