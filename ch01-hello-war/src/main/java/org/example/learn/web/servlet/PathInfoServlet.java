package org.example.learn.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PathInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String title = "pathInfo";
        Map<String, String> pathInfo = demoPath(request);

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");
        out.println("<title>" + title + "</title>");
        out.println("</head>");

        out.println("<body bgcolor=\"white\">");
        pathInfo.forEach((key, Value) -> {
            out.println("<h3>" + key + "=" + Value + "</h3>");
        });
        out.println("</body>");
        out.println("</html>");
    }

    private Map<String, String> demoPath(HttpServletRequest request) {
        Map<String, String> pathInfo = new HashMap<>();

        File cwd = new File("");
        pathInfo.put("cwd.AbsolutePath", cwd.getAbsolutePath());

        File bundle = new File(cwd.getParent(), "/WEB-INF/classes/bundle");
        pathInfo.put("bundle.AbsolutePath", bundle.getAbsolutePath());

        String servletPathRealPath = request.getServletContext().getRealPath(request.getServletPath());
        pathInfo.put("servletPath.realPath", servletPathRealPath);

        return pathInfo;
    }
}
