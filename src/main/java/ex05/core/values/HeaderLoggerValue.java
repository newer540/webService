package ex05.core.values;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.Container;
import ex05.core.Value;
import ex05.core.ValueContext;

import javax.servlet.ServletException;

public class HeaderLoggerValue implements Value {
    @Override
    public void setContainer(Container container) {

    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValueContext valueContext) throws ServletException {
        System.out.println("Header Logger Value");
        valueContext.invokeNext(request,response);
    }
}
