package ex05.connector.http;


import ex05.connector.Connector;
import ex05.connector.Request;
import ex05.connector.Response;
import ex05.core.Container;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class DefaultConnector implements Connector,Lifecycle ,Runnable {
    private boolean stopped=false;
    private Container container;
    private ServerSocket serverSocket;
    private Queue<HttpProcessor> processorQueue=new LinkedList<>();
    private int currentProcessorsNum;

    public DefaultConnector(){
        try {
            init();
            start();
        }catch (LifecycleException e){
            e.printStackTrace();
        }
    }



    /**
     * 创建Serversocket，等待连接。
     * 为连接Socket分配HttpProcessor
     */
    @Override
    public void run() {
        while (!stopped){
            try {
                Socket socket=serverSocket.accept();
                HttpProcessor httpProcessor=processorQueue.poll();
                if(httpProcessor==null){
                    if(Constant.maxProcessors<=0||currentProcessorsNum>= Constant.maxProcessors)
                        continue;
                    currentProcessorsNum++;
                    httpProcessor=new HttpProcessor(this,currentProcessorsNum+"th");
                    httpProcessor.start();
                }
                httpProcessor.assign(socket);
            } catch (Exception e) {

            }
        }

    }

    //单例获取
    private ServerSocket open() throws IOException {
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        ServerSocket serverSocket = serverSocketFactory.createServerSocket(Constant.port);
        return serverSocket;
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
    public Request createRequest() {
        return null;
    }

    @Override
    public Response createResponse() {
        return null;
    }


    //Lifecycle 接口方法

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
        try {
            serverSocket=open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //维护HttpProcessor对象池，提高并发和避免频繁创建对象的损失
    @Override
    public void start() throws LifecycleException {
        while(currentProcessorsNum<Constant.minProcessors){
            currentProcessorsNum++;
            HttpProcessor httpProcessor = new HttpProcessor(this,currentProcessorsNum+"th");
            httpProcessor.start();
            recycle(httpProcessor);
        }
    }
    public void recycle(HttpProcessor httpProcessor){
        processorQueue.add(httpProcessor);
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

    public static void main(String[] args) {
        DefaultConnector defaultConnector = new DefaultConnector();
        try {
            defaultConnector.init();
            defaultConnector.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}
