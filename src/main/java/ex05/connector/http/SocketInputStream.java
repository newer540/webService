package ex05.connector.http;

import ex05.core.Constants;
import ex05.connector.http.assist.HttpHeader;
import ex05.connector.http.assist.RequestLine;

import java.io.*;

/**
 * 将字节流过滤成字符流，并提供 访问 http请求各个部分的操作
 */
public class SocketInputStream extends InputStream {
    public static void main(String[] args) {

    }
    private InputStream inputStream;
    private BufferedReader reader;
    public SocketInputStream(InputStream inputStream){
        this.inputStream=inputStream;
    }

    public void readRequestLine(RequestLine requestLine) throws IOException {
        reader=new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
        String requestLineStr = reader.readLine();
        //  "GET /servlet/name;jsessionid=412?name=zhaodi&password=123456 HTTP/1.1"
        int index1=requestLineStr.indexOf(" ");
        if(index1!=-1){
            int index2=requestLineStr.indexOf(" ",index1+1);
            if(index1<index2){
                //method
                String method=requestLineStr.substring(0,index1);
                requestLine.setMethod(method.getBytes(Constants.Charaset));
                requestLine.setMethodEnd(requestLine.getMethod().length);
                //url    http://index.html;jsessionid=123?name=zhaodi&password=123456
                String url=requestLineStr.substring(index1+1,index2);
                int index=0;
                          //处理  queryString
                index=url.indexOf("?");
                if(index!=-1){
                    String queryString=url.substring(index+1);
                    requestLine.setQueryString(queryString.getBytes(Constants.Charaset));
                    requestLine.setQueryStringEnd(requestLine.getQueryString().length);
                }
                        //处理 ://
                index=url.indexOf("://");
                if(index!=-1){
                    url=url.substring(index+2);
                }
                      //处理 jsessionid
                index=url.indexOf(";jsessionid=");
                if(index!=-1){
                    String jsessionid=url.substring(index+12);
                    requestLine.setJsessionid(jsessionid.getBytes(Constants.Charaset));
                    requestLine.setJsessionidEnd(requestLine.getJsessionid().length);
                    url=url.substring(0,index);
                }
                requestLine.setUrl(url.getBytes(Constants.Charaset));
                requestLine.setUrlEnd(requestLine.getUrl().length);
                //protocol
                String protocol=requestLineStr.substring(index2+1);
                requestLine.setProtocol(protocol.getBytes(Constants.Charaset));
                requestLine.setProtocolEnd(requestLine.getProtocol().length);
            }else  throw new IOException("请求行出错");
        }else  throw new IOException("请求行出错");
    }

    public void readRequestHeader(HttpHeader httpHeader) throws IOException {
        String headerStr = reader.readLine();
        if(headerStr==null||headerStr.equals("")){  //  头部结束
            httpHeader.nameEnd=0;
            httpHeader.valueEnd=0;
            return;
        }
        int index=headerStr.indexOf(":");
        String name=headerStr.substring(0,index).trim();
        String value=headerStr.substring(index+1).trim();


        httpHeader.value=name.getBytes(Constants.Charaset);
        httpHeader.valueEnd=httpHeader.value.length;
        httpHeader.name=name.getBytes(Constants.Charaset);
        httpHeader.nameEnd=httpHeader.name.length;
    }





    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}
