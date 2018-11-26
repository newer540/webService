package ex05.connector.http;

import ex05.core.Constants;
import ex05.connector.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;

public class HttpResponse implements Response {
    private OutputStream outputStream;
    private HttpRequest request;

    private boolean isChunked=false;
    //protocol
    private byte[] protocol;
    private int protocolEnd;
    //status
    private int status;
    //statusDesc
    private String statusDesc;
    private int getStatusEnd;

    //ResponseHeaders
    private HashMap<String,String> headers=new HashMap<>();

    private String connection="keep-alive";

    private ResponseWriter writer;

    private String contentType;
    public boolean isChunked() {
        return isChunked;
    }

    public void setChunked(boolean chunked) {
        isChunked = chunked;
    }

    public void recycle(){
        writer=null;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        if(connection){
            this.connection="keep-alive";
        }else
            this.connection="close";
    }



    @Override
    public PrintWriter getWriter() throws IOException {

        if(writer==null){
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,Constants.Charaset);
            writer=new ResponseWriter(outputStreamWriter);
            writer.printlnOfResponseStatus("HTTP/1.1 200 ok");
            writer.printlnOfResponseStatus("Content-Type: "+getContentType());
            writer.printlnOfResponseStatus("Connection: "+getConnection());
          //  if(isChunked)
            writer.printlnOfResponseStatus("Transfer-Encoding: chunked");
            writer.printlnOfResponseStatus("");
        }

        return writer;
    }

    public PrintWriter getSystemWriter() throws UnsupportedEncodingException {
        if(writer==null){
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,Constants.Charaset);
            writer=new ResponseWriter(outputStreamWriter);
            writer.printlnOfResponseStatus("HTTP/1.1 100 Continue");
            writer.printlnOfResponseStatus("");
        }
        return writer;
    }



    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {
        headers.put(s,s1);
    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }



    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {
        contentType=s;
    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public void setStatusDesc(String s) {
        statusDesc=s;
    }
}
