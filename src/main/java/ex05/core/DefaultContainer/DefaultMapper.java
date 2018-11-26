package ex05.core.DefaultContainer;

import ex05.connector.Request;
import ex05.core.Constants;
import ex05.core.Container;
import ex05.core.Mapper;
import ex05.core.values.basicValues.SimpleWrapperValue;


import java.util.HashMap;
import java.util.Map;

public class DefaultMapper implements Mapper {
    private Container container;
    private String protocol;
    private Map<String,Container> map=new HashMap<>();
    public DefaultMapper(Container container){
        this.container=container;
    }
    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol=protocol;
    }

    @Override
    public Container map(Request request, boolean update) {
        String url = request.getUrl();  //  假定 url 中 最后一定是 /servletName.do 结束
        if(url.endsWith(Constants.SERVLET_EXTENSION)){
            int index1=url.lastIndexOf("/");
            int index2=url.lastIndexOf(".do");
            url=url.substring(index1+1,index2);
        }else{
            url=Constants.ERRO_SERVLET_NAME;
        }
        Container container=map.get(url);
        if(container==null){  //通过父类的loader(且子类不保存该loader)和url构造该子容器
            container = new DefaultWrapper(url, getContainer());
            SimpleWrapperValue simpleWrapperValue = new SimpleWrapperValue(container);
            container.setBasicValue(simpleWrapperValue);
            map.put(url,container);

        }
        return container;
    }
}
