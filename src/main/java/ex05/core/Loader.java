package ex05.core;

import org.apache.catalina.Lifecycle;

import javax.servlet.Servlet;

public interface Loader extends Lifecycle {
    Servlet load(String servletName);
}
