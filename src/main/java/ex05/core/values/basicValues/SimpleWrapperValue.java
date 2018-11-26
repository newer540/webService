package ex05.core.values.basicValues;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.Container;
import ex05.core.DefaultContainer.DefaultWrapper;
import ex05.core.Value;
import ex05.core.ValueContext;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

public class SimpleWrapperValue implements Value {
    private Container container;
    public SimpleWrapperValue(Container container){
        this.container=container;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValueContext valueContext) throws ServletException {
        DefaultWrapper container = (DefaultWrapper) getContainer();

        Servlet servlet = container.allocate();
        try {
            servlet.service(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
