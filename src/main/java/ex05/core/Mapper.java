package ex05.core;

import ex05.connector.Request;

/**
 * Mapper 作为Container的组件，用来查找子容器
 * getContainer 返回该映射器属于哪个Container
 * getProtocol 标识该Mapper负责哪种协议，一个容器可以含有多个映射器来支持不同的协议
 * map()返回查找的子容器
 */
public interface Mapper {
    Container getContainer();
    void setContainer(Container container);
    String getProtocol();
    void setProtocol(String protocol);
    Container map(Request request,boolean update);
}
