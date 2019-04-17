package servlet;

import db.DataBase;
import exception.servlet.ServletException;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(Request req, Response res) throws ServletException, IOException {
        res.forward("/user/login.html");
    }

    @Override
    protected void doPost(Request req, Response res) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        if(DataBase.findUserById(userId).getPassword().equals(password)){
            res.forward("/index.html");
            res.addCookie("logined","true");
            return;
        }
        res.forward("/user/login_failed.html");
        res.addCookie("logined","false");

    }
}
