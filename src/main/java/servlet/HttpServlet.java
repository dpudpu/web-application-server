package servlet;

import exception.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import webserver.Request;
import webserver.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Response.class);
    private static final String DEFAULT_PATH = "./webapp";

    public void service(Request req, Response res) throws IOException{
        try {
            String method = req.getMethod();
            if (method.equals("GET")) {
                doGet(req, res);
            } else if (method.equals("POST")) {
                readBody(req);
                doPost(req, res);
            } else {
                log.error("잘못된 메소드 사용");
                throw new IllegalArgumentException();
            }
        }catch (Exception e){
            log.error(e.getMessage());
            // TODO 에러 처리후 센드리다이렉트?
        }

        String path = DEFAULT_PATH+res.getResponseDispatcher();

        DataOutputStream dos = res.getDos();
        File file = new File(path);
        if (file.exists()) {
            res.response200Header(dos, file.length());
            byte[] body = Files.readAllBytes(new File(path).toPath());
            res.responseBody(dos, body);
        } else {
            res.response400Header(dos);
        }
    }

    private void readBody(Request req) throws IOException {
        int contentLength = Integer.parseInt(req.getHeader("Content-Length"));
        String body = IOUtils.readData(req.getBr(), contentLength);
        req.setParameter(body);
    }

    protected abstract void doGet(Request req, Response res) throws ServletException, IOException;

    protected abstract void doPost(Request req, Response res) throws ServletException, IOException;
}
