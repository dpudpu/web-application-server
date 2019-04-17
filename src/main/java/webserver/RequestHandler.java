package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DefaultServlet;
import servlet.HttpServlet;
import servlet.SignUpServlet;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            Request request = new Request(br);
            Response response = new Response(dos);

            HttpServlet httpServlet;

            if(request.getPath().equals("/user/create")){
                httpServlet = new SignUpServlet();
            }else {
                httpServlet = new DefaultServlet();
            }

            httpServlet.service(request, response);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
