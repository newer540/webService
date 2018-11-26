package ex05.core;

import ex05.connector.Request;
import ex05.connector.Response;

import javax.servlet.ServletException;

/**
 * 是  request 和 response 的 处理逻辑单元
 */
public interface Value {
    /**
     * base Value 需要使用Wrapper 来获取对应的Servlet实例
     */
    void setContainer(Container container);
    Container getContainer();
    String getInfo();
    void invoke(Request request,Response response,ValueContext valueContext) throws ServletException;
}
