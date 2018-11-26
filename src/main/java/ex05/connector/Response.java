package ex05.connector;

import javax.servlet.http.HttpServletResponse;

public interface Response extends HttpServletResponse {
    void setStatusDesc(String s);
}
