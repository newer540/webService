package ex05.core;

import ex05.connector.Request;

/**
 * Context 关于 PipeLine 和 Loader 和 parent等 容器公用的操作已交给Container接口和
 * ContainerBase类
 * 此时，只需负责Mapper组件相关的操作
 */
public interface Context extends Container {
    //pipeline
    //loader
    //map
    void setMapper(Mapper mapper);
    Mapper getMapper();
    Container map(Request request,boolean update);


}
