package servlet;

import exception.servlet.ServletException;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(Request req, Response res) throws ServletException, IOException {
        if(res.getCookie("logined",req).equals("true")){
            res.forward("/user/list.html");
            return;
        }
        res.forward("/user/login.html");
    }

    @Override
    protected void doPost(Request req, Response res) throws ServletException, IOException {

    }
}
