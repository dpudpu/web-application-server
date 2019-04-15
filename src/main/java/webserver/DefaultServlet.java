package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultServlet {
    private static final String PATH = "./webapp";

    public void service(Request req, Response res) throws IOException {
        DataOutputStream dos = res.getDos();

        File file = new File(PATH + req.getPath());
        if (file.exists()) {
            res.response200Header(dos, file.length());
            byte[] body = Files.readAllBytes(new File(PATH + req.getPath()).toPath());
            res.responseBody(dos, body);
        } else {
            res.response400Header(dos);
        }
    }
}
