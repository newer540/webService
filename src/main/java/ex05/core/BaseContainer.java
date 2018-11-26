package ex05.core;




public  abstract   class BaseContainer implements Container {
    private Loader classLoader;  //此时，对于使用者来说，只需知道它能完成类的加载，而他
                                      //的实现被屏蔽了
    private Pipeline pipeline;
    private Container parent;

    public Pipeline getPipeline(){
        return pipeline;
    }
    public void setPipeline(Pipeline pipeline){
        this.pipeline=pipeline;
    }

    @Override
    public Value getBasicValue() {
        return pipeline.getBasicValue();
    }

    @Override
    public void setBasicValue(Value value) {
        pipeline.setBasicValue(value);
    }

    @Override
    public void addValue(Value value) {
        pipeline.addValue(value);
    }

    @Override
    public Value[] getValue() {
        return pipeline.getValue();
    }

    @Override
    public void removeValue(Value value) {
        pipeline.removeValue(value);
    }

    @Override
    public Value getFirst() {
        return pipeline.getFirst();
    }



    public Loader getLoader() {
        if(classLoader==null)
            return parent.getLoader();
        return classLoader;
    }

    public void setLoader(Loader classLoader) {
        this.classLoader=classLoader;
    }

    @Override
    public Container findParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {
        parent=container;
    }
}
