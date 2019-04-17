package servlet;

import db.DataBase;
import exception.servlet.ServletException;
import model.User;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public class SignUpServlet extends DefaultServlet {

    @Override
    protected void doPost(Request req, Response res) throws ServletException, IOException{
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);;

        res.setResponseDispatcher("/index.html");

//        res.sendRedirect("/index.html");
    }
}
