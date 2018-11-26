package ex05.core;


import ex05.connector.Request;
import ex05.connector.Response;
import org.apache.catalina.Lifecycle;

/**
 * 提供 invoke(request,response)接口，来实现  请求servlet
 */
public interface Container extends Lifecycle {
    void invoke(Request request, Response response);



    //组件相关
    Loader getLoader();
    void setLoader(Loader Loader);
    Container findParent();
    void setParent(Container container);

    Pipeline getPipeline();
    void setPipeline(Pipeline pipeline);

    //basicValue
    Value getBasicValue();
    void setBasicValue(Value value);

    //Value
    void addValue(Value value);
    Value[] getValue();
    void removeValue(Value value);
    Value getFirst();



}
