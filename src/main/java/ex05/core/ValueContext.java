package ex05.core;

import ex05.connector.Request;
import ex05.connector.Response;

import javax.servlet.ServletException;

public interface ValueContext {
    void invokeNext(Request request, Response response) throws ServletException;
}
