package ex05.core;

import javax.servlet.Servlet;

public interface Wrapper extends Container {

    void load();
    Servlet allocate();


}
