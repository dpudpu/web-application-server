package servlet;

import exception.servlet.ServletException;
import webserver.Request;
import webserver.Response;

import java.io.IOException;


public class DefaultServlet extends HttpServlet {
    private static final String DEFAULT_PATH = "/index.html";

    @Override
    protected void doPost(Request req, Response res) throws ServletException, IOException {
    }

    @Override
    protected void doGet(Request req, Response res) throws ServletException, IOException {
        String path = req.getPath();
        if (path.equals("/"))
            res.forward(path + DEFAULT_PATH);
        else
            res.forward(path);
    }
}
