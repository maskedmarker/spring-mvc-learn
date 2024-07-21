package org.example.learn.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorCode500Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int HTTP_CODE_500 = 500;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HTTP_CODE_500);
    }
}
