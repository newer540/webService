package ex05.core;

public interface Engine extends Container {
    //Container 的层次结构

    void addChild(Container container);
    void removeChild(Container container);
    Container findChild(String name);
    Container[]  findChildren();

    /*


        @Override
    public void addChild(Container container) {

    }

    @Override
    public void removeChild(Container container) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }
     */
}
