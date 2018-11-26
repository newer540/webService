package ex05.core.DefaultContainer;

import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.*;
import ex05.core.loader.SimpleLoader;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.util.LifecycleSupport;

public class DefaultContext extends BaseContainer implements Context {
    private Mapper mapper;
    public DefaultContext(){
        createMapper();
        createPipeLine();
        createLoader();
    }
    public DefaultContext(Container parent){
        createMapper();
        createPipeLine();
        setParent(parent);
    }
    private void createMapper(){
        mapper=new DefaultMapper(this);
    }
    private void createPipeLine(){
        //pipeline
        DefaultPipeline defaultPipeline = new DefaultPipeline();
        setPipeline(defaultPipeline);
    }
    private void createLoader(){
        SimpleLoader simpleLoader = new SimpleLoader();
        setLoader(simpleLoader);
    }
    @Override
    public void setMapper(Mapper mapper) {
        this.mapper=mapper;
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public Container map(Request request, boolean update) {
        return mapper.map(request,update);
    }

    @Override
    public void invoke(Request request, Response response) {
        Pipeline pipeline = getPipeline();
        pipeline.invoke(request,response);
    }

    private LifecycleSupport lifecycle=new LifecycleSupport(this);
    private boolean started=false;
    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycle.addLifecycleListener(lifecycleListener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return lifecycle.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycle.removeLifecycleListener(lifecycleListener);
    }

    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {
        if(started){
            throw new LifecycleException("Default has already started");
        }
        started=true;

        Loader loader = getLoader();
        if(loader!=null&&(loader instanceof Lifecycle))
            ((Lifecycle)loader).start();


        Pipeline pipeline = getPipeline();
        if(pipeline!=null&&(pipeline instanceof Lifecycle))
            ((Lifecycle)pipeline).start();
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT,null);
        lifecycle.fireLifecycleEvent(START_EVENT,null);
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT,null);


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
