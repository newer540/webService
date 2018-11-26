package ex05.core.DefaultContainer;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.Container;
import ex05.core.Pipeline;
import ex05.core.Value;
import ex05.core.ValueContext;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class DefaultPipeline implements Pipeline {
    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener lifecycleListener) {

    }

    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {
        System.out.println(getClass().getName()+".start().");
    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }

    protected class DefaultValueContext implements ValueContext {
        private int stage=0;
        @Override
        public void invokeNext(Request request, Response response) throws ServletException {
            int subscript=stage;
            stage++;
            if(subscript<values.size()){
                Value value=values.get(subscript);
                value.invoke(request,response,this);
            }else if (subscript==values.size()&&basic!=null){
                basic.invoke(request,response,this);
            }else{
                //throw new ServletException("defaultPipeline.noValue");
            }
        }
    }
    private List<Value> values=new ArrayList<>();
    private Value basic;
    private Container container;

    @Override
    public void invoke(Request request, Response response) {
        ValueContext valueContext=new DefaultValueContext();
        try {
            valueContext.invokeNext(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Value getBasicValue() {
        return basic;
    }

    @Override
    public void setBasicValue(Value value) {
        basic=value;
    }

    @Override
    public void addValue(Value value) {
        values.add(value);
    }

    @Override
    public Value[] getValue() {
        return values.toArray(new Value[values.size()]);
    }

    @Override
    public void removeValue(Value value) {
        values.remove(value);
    }

    @Override
    public Value getFirst() {
        return values.get(0);
    }

    @Override
    public void setContainer() {

    }

    @Override
    public Container getContainer() {
        return container;
    }
}
