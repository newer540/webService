package ex05.connector.http;
import ex05.core.Constants;
import ex05.core.Container;
import ex05.connector.http.assist.DefaultHeaders;
import ex05.connector.http.assist.HttpHeader;
import ex05.connector.http.assist.RequestLine;
import ex05.core.staticResourceProcessor.StaticResourceProcessor;
import org.apache.tomcat.util.http.FastHttpDateFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


public class HttpProcessor extends Thread {
    private boolean avaiable=false;
    private boolean stopped;
    private Socket socket;
    private DefaultConnector defaultConnector;
    public HttpProcessor(DefaultConnector defaultConnector, String threadName){
        setName(threadName);
        this.defaultConnector = defaultConnector;
    }
    public HttpProcessor(DefaultConnector defaultConnector){
        this.defaultConnector = defaultConnector;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }



    @Override
    public void run() {
        while(!stopped){
            Socket socket=await();
            if(socket==null)   //增强了系统可靠性*******haha
                continue;
        //    System.out.println("Thread:"+Thread.currentThread().getName()+" 处理连接");
            try {
                process(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //    System.out.println("Thread:"+Thread.currentThread().getName()+" 连接结束");
            defaultConnector.recycle(this);
        }
    }

    /**
     * 使用循环来实现Keep-alive,
     * 若是不支持Keep-alive的连接，或者出错，通过设置标志位此次信息处理完就退出循环，即关闭连接
     *
     */
    private HttpRequest request;
    private HttpResponse response;
    private boolean keepAlive;
    private boolean sendAck;
    private void process(Socket socket) throws IOException {
        //System.out.println(socket);
        request=new HttpRequest();
        response=new HttpResponse();

        SocketInputStream inputStream=new SocketInputStream(socket.getInputStream());
        OutputStream outputStream=null;

        boolean ok=true;  //表示是否有错误发生
        boolean finishResponse=true;  //是否要调用Response的finishResponse()
        keepAlive=true;   //默认为http1.1，支持连接 ，并且同一个连接将不会变，放在循环外部
        sendAck=false;  //默认 不启动
        int count=0;
        while(!stopped&&ok&&keepAlive){        //不停地 循环解析同一个Socket中的请求
            finishResponse=true;

            try {
                //通过SocketInputStream将  输入流字节 转为 request对象
                request.setInputStream(inputStream);
                request.setResponse(response);
                outputStream = socket.getOutputStream();
                response.setOutputStream(outputStream);
                response.setRequest(request);
                response.setHeader("Server","tingTomcatServer");
            }catch (Exception e){
                System.err.println("prcoess.create");
                ok=false;
            }

            try {
                if(ok){
                    parseConnection(socket);
                    parseRequest(inputStream,outputStream);
                    if (!request.getProtocol().startsWith("HTTP/0"))
                        parseHeader(inputStream);
                }

                response.setHeader("Date",FastHttpDateFormat.getCurrentDate());
                if(ok){
                    String url = request.getUrl();
                    System.out.println(url);
                    if(url.startsWith("/servlet/")){
                        // System.out.println("*********************"+count++);
                        Container container = defaultConnector.getContainer();
                        //     System.out.println(request.getUrl());
                        container.invoke(request,response);
                    }else{
                        StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                        staticResourceProcessor.process(request,response);
                    }



                }
                if("close".equals(response.getConnection()))
                    keepAlive=false;

                request.recycle();
                response.recycle();
            }catch (Exception e){
               // e.printStackTrace();
                ok=false;

            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*


     */

    private void parseConnection(Socket socket){
        request.setInetAddress(socket.getInetAddress());
        request.setServerPort(socket.getLocalPort());
        request.setSocket(socket);
    }
    private void parseRequest(SocketInputStream inputStream, OutputStream outputStream) throws IOException {
        RequestLine requestLine=new RequestLine();
        //转换
            //requestLine
        inputStream.readRequestLine(requestLine);


        //处理   对  HttpRequest的一些状态的设置也可能会联动到HttpResponse的一些状态
           //requestLine
        addRequestLineToRequest(requestLine,request,response);

        String protocol=request.getProtocol();
        if(protocol.equals("HTTP/1.0"))
            keepAlive=false;

    }
    private void parseHeader(SocketInputStream inputStream) throws IOException {
        //requestHeader
        HttpHeader httpHeader=new HttpHeader();
        inputStream.readRequestHeader(httpHeader);
        while(true){
            if(httpHeader.nameEnd==0||httpHeader.valueEnd==0)
                break;
          //  request.addHeader(new String(httpHeader.name,0,httpHeader.nameEnd),
           //         new String(httpHeader.value,0,httpHeader.valueEnd));
            //authorization   accept-language content-length-name content-type-name host-name connection-name
            //expect-name  transfer-encoding
            if(httpHeader.equals(DefaultHeaders.AUTHORIZATION_NAME)){
                request.setAuthorization(new String(httpHeader.value,Constants.Charaset));
            }else if (httpHeader.equals(DefaultHeaders.ACCEPT_LANGUAGE_NAME)){

            }else if (httpHeader.equals(DefaultHeaders.COOKIE_NAME)){

            }else if (httpHeader.equals(DefaultHeaders.CONTENT_LENGTH_NAME)){

            }else if (httpHeader.equals(DefaultHeaders.CONTENT_TYPE_NAME)){
                request.setContentType(Integer.valueOf(new String(httpHeader.value,Constants.Charaset)));
            }else if (httpHeader.equals(DefaultHeaders.HOST_NAME)){

            }else if (httpHeader.equals(DefaultHeaders.CONNECTION_NAME)){

            }else if (httpHeader.equals(DefaultHeaders.EXPECT_NAME)){
                String value=new String(httpHeader.value,Constants.Charaset);
                String protocol = request.getProtocol();
                if(value.equals("100-continue")&&protocol.equals("HTTP/1.1")){  //说明这个请求是向服务器询问是否。。。。
                    sendAck=true;
                    ackRequest(response);
                    /*
                    if(connector.isChunkingAllowed())
                    response.setAllowChunking(true);                   ??????.....!!!!好吧 我就默认开启分块  这是个bug
                     */

                }

            }else if (httpHeader.equals(DefaultHeaders.TRANSFER_ENCODING_NAME)){

            }
            inputStream.readRequestHeader(httpHeader);
        }
    }
    private void ackRequest(HttpResponse response) throws UnsupportedEncodingException {
        if(sendAck==true)
        response.getSystemWriter();
    }
    private void addRequestLineToRequest(RequestLine requestLine, HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        //method
        String method=new String(requestLine.getMethod(),Constants.Charaset);
        request.setMethod(method);
        //url
        String url=new String(requestLine.getUrl(),Constants.Charaset);
        request.setUrl(url);
        //jsessionid
        if (requestLine.getJsessionidEnd()!=0){
            String jsessionid=new String(requestLine.getUrl(),Constants.Charaset);
            request.setJsessionid(jsessionid);
            request.setJsessionIdFromUrl(true);
        }
        //queryStr
        if(requestLine.getUrlEnd()!=0){
            String queryStr=new String(requestLine.getUrl(),Constants.Charaset);
            request.setQueryString(queryStr);
        }
        //protocol
        String protocol=new String(requestLine.getUrl(),Constants.Charaset);
        request.setProtocol(protocol);
    }

    public synchronized void assign(Socket socket){
        while(avaiable){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.socket=socket;
        avaiable=true;
        notifyAll();
    }
    public synchronized Socket await(){
        while(!avaiable){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Socket socket=this.socket;
        avaiable=false;
        notifyAll();
        return socket;
    }
}
