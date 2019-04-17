package servlet;

import db.DataBase;
import model.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SignUpServletTest {
    private String userId;
    private String password;
    private String name;
    private String email;

    @Before
    public void setUp() {
        userId = "userId";
        password = "password";
        name = "name";
        email = "email";
        User user1 = new User(userId, password, name, email);
        DataBase.addUser(user1);
        User user2 = new User("user", password, name, email);
        DataBase.addUser(user2);
    }


    @Test
    public void 회원가입_테스트() {
        assertThat(DataBase.findUserById("userId").getPassword(), is(password));
        assertThat(DataBase.findAll().size(), is(2));
    }

    @Test
    public void 로그인_테스트() {
        assertThat(DataBase.findUserById("userId").getPassword().equals("password"), is(true));
    }
}