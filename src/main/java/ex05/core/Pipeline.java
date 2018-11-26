package ex05.core;

import ex05.connector.Request;
import ex05.connector.Response;
import org.apache.catalina.Lifecycle;

/**
 * 数据对象，用来存储Value
 * 表示对 每一个请求和相应 的过滤性处理任务，每个任务用Value表示
 * 每个管道都由一系列Value和一个基础Value,依次invoke(),最后处理基础Value
 */

public interface Pipeline  extends Lifecycle {
    void invoke(Request request, Response response);

    //basicValue
    Value getBasicValue();
    void setBasicValue(Value value);

    //Value
    void addValue(Value value);
    Value[] getValue();
    void removeValue(Value value);
    Value getFirst();

    //Container
    void setContainer();
    Container getContainer();
}
