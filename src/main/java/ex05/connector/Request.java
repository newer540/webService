package ex05.connector;

import javax.servlet.http.HttpServletRequest;

public interface Request extends HttpServletRequest {
    String getUrl();
    void setUrl(String url);
}
