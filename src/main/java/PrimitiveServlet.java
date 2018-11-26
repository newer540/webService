import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("from service");
        PrintWriter out=servletResponse.getWriter();
        out.println("Hello. Roses are red.");
        out.print("Violets are blue");
        out.flush();
    }

    public String getServletInfo() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(22351);
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
