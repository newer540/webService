import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Ex04PrimitiveServlet   implements Servlet {
    public static void main(String[] args) {

    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println(getClass().getName()+" Servlet init.");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println(getClass().getName());
        PrintWriter writer = response.getWriter();
        writer.print("Hello ,");
        writer.println("赵迪");
        writer.println("    Hello,Mike");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println(getClass().getName()+" Servlet destory.");
    }
}
