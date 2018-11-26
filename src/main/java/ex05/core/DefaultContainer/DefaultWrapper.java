package ex05.core.DefaultContainer;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.*;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

import javax.servlet.Servlet;

public class DefaultWrapper extends BaseContainer implements Wrapper {
    private String servletName;
    private Servlet servlet;


    public DefaultWrapper(String servletName){
        this.setServletName(servletName);
        createPipeLine();
    }
    /**
     * 构造ServletName.servlet 对应的Wrapper实例
     * 并为Wrapper创建ClassLoader和Pipeline
     *
     * DefaultWrapper(String servletName)  没有类加载器说明ta有父容器，使用父容器的类加载器
     *
     * DefaultWrapper(String servletName,Loader loader) 使用自己的类加载器
     * @param servletName  指明对应的Servlet
     * @param container    指明父容器
     */
    public DefaultWrapper(String servletName,Container container){
        setServletName(servletName);
        Loader loader = container.getLoader();
        servlet=loader.load(servletName);
        if(servlet==null)
            servlet=loader.load("ErroServlet");
        setParent(container);
        createPipeLine();
    }
    public DefaultWrapper(String servletName,Loader loader){
        setServletName(servletName);
        setLoader(loader);
        createPipeLine();
    }
    private void createPipeLine(){
        //pipeline
        DefaultPipeline defaultPipeline = new DefaultPipeline();
        setPipeline(defaultPipeline);
    }


    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }


    @Override
    public Container findParent() {
        return super.findParent();
    }

    @Override
    public void setParent(Container container) {
        super.setParent(container);
    }

    @Override
    public void load() {
        Loader loader = getLoader();
        servlet = loader.load(servletName);
    }

    @Override
    public Servlet allocate() {
        if (servletName!=null){
            if(servlet==null)
                load();
            return servlet;
        }
        return null;
    }

    @Override
    public void invoke(Request request, Response response) {
        Pipeline pipeline = getPipeline();
        pipeline.invoke(request,response);
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener lifecycleListener) {

    }

    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {
        System.out.println(getClass().getName()+".start().");
    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }
}
