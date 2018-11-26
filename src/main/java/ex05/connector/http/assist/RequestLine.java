package ex05.connector.http.assist;

public class RequestLine {
    //method
    private byte[] method;
    private int methodEnd;
    //url
    private byte[] url;
    private int urlEnd;
    //jsessionid
    private byte[] jsessionid;
    private  int jsessionidEnd;
    //queryString
    private byte[] queryString;
    private int queryStringEnd;
    //protocol
    private byte[] protocol;
    private int protocolEnd;

    public byte[] getMethod() {
        return method;
    }

    public void setMethod(byte[] method) {
        this.method = method;
    }

    public int getMethodEnd() {
        return methodEnd;
    }

    public void setMethodEnd(int methodEnd) {
        this.methodEnd = methodEnd;
    }

    public byte[] getUrl() {
        return url;
    }

    public void setUrl(byte[] url) {
        this.url = url;
    }

    public int getUrlEnd() {
        return urlEnd;
    }

    public void setUrlEnd(int urlEnd) {
        this.urlEnd = urlEnd;
    }

    public byte[] getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(byte[] jsessionid) {
        this.jsessionid = jsessionid;
    }

    public int getJsessionidEnd() {
        return jsessionidEnd;
    }

    public void setJsessionidEnd(int jsessionidEnd) {
        this.jsessionidEnd = jsessionidEnd;
    }

    public byte[] getQueryString() {
        return queryString;
    }

    public void setQueryString(byte[] queryString) {
        this.queryString = queryString;
    }

    public int getQueryStringEnd() {
        return queryStringEnd;
    }

    public void setQueryStringEnd(int queryStringEnd) {
        this.queryStringEnd = queryStringEnd;
    }

    public byte[] getProtocol() {
        return protocol;
    }

    public void setProtocol(byte[] protocol) {
        this.protocol = protocol;
    }

    public int getProtocolEnd() {
        return protocolEnd;
    }

    public void setProtocolEnd(int protocolEnd) {
        this.protocolEnd = protocolEnd;
    }
}
