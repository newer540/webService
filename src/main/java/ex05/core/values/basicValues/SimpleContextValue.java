package ex05.core.values.basicValues;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.Container;
import ex05.core.Context;
import ex05.core.Value;
import ex05.core.ValueContext;

import javax.servlet.ServletException;

public class SimpleContextValue implements Value {
    private Container container;
    public SimpleContextValue(Container container){
        this.container=container;
    }
    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValueContext valueContext) throws ServletException {
        Context container = (Context) getContainer();
        Container subContainer = container.map(request, true);
        subContainer.invoke(request,response);
    }
}
