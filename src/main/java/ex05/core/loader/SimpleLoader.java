package ex05.core.loader;

import ex05.core.Container;
import ex05.core.Loader;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * 负责Servlet.class 字节码的加载 ,使用URLClasslLoader
 * 由于Tomcat的容器分层，且每一个容器都有对应的类加载器实例，且
 * java类中Class对象是否相等首先取决于是否是同一个类加载器
 * （因为不同的加载器意味不同的路径，即不同的包）加载出来的，所以需要允许通过类文件——》类加载
 * 器--》容器。
 */
public class SimpleLoader implements Loader {
    private static final String WEB_ROOT=System.getProperty("user.dir")+ File.separator+"webRoot";
    private URLClassLoader loader;
    private Container container;

    //container
    public void setContainer(Container container){
        this.container=container;
    }
    public Container getContainer(){
        return container;
    }
    //loader
    public SimpleLoader(){
        loader=null;
        URLStreamHandler urlStreamHandler=null;
        try {
            URL[]  urls=new URL[1];
            File file=new File(WEB_ROOT);
            String repository=(new URL("file",null,file.getCanonicalPath()+File.separator)).toString();
            urls[0]=new URL(null,repository,urlStreamHandler);
            loader=new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Servlet load(String servletName)  {
        Class myclass=null;
        try {
            myclass=loader.loadClass(servletName);
            return (Servlet) myclass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("111");
        return null;
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
