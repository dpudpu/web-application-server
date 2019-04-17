package servlet;

import db.DataBase;
import model.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SignUpServletTest {

    @Test
    public void 회원가입_테스트(){

        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user1 = new User(userId, password, name, email);
        DataBase.addUser(user1);
        User user2 = new User("user", password, name, email);
        DataBase.addUser(user2);

        assertThat(DataBase.findUserById("userId").getPassword(), is(password));
        assertThat(DataBase.findAll().size(),is(2));
    }
}