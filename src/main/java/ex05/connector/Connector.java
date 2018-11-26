package ex05.connector;

import ex05.core.Container;


public interface Connector {
    Container getContainer();
    void setContainer(Container container);
    Request createRequest();
    Response createResponse();

}
