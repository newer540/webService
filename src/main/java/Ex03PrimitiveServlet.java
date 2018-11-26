import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Ex03PrimitiveServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println(this.getClass().getName()+" init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
      //  response.setCharacterEncoding("ASCII");
        System.out.println(getClass().getName());
        PrintWriter writer = response.getWriter();
        writer.print("Hello,");
        writer.println("Mike.");
        writer.print("  ");
        writer.println("Hello,Nancy.");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName()+" destroy");
    }

    public static void main(String[] args) {
        System.out.println(111);
    }
}
