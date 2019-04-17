package servlet;

import exception.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import webserver.Request;
import webserver.Response;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Response.class);
    private static final String DEFAULT_ROOT = "./webapp";

    private String method;

    public void service(Request req, Response res) throws ServletException, IOException {
        method = req.getMethod();

        if (method.equals("GET")) {
            doGet(req, res);
        } else if (method.equals("POST")) {
            readBody(req);
            doPost(req, res);
        } else {
            log.error("잘못된 메소드 사용");
            throw new IllegalArgumentException();
        }

        writeResponse(res);
    }

    private void writeResponse(Response res) throws IOException {
        String path = DEFAULT_ROOT + res.getResponseDispatcher();

        DataOutputStream dos = res.getDos();
        File file = new File(path);
        if (file.exists()) {
            writeResponseLineAndHeaders(res, dos, file);
            res.responseBody(dos, Files.readAllBytes(new File(path).toPath()));
            return;
        }
        res.response400Header(dos);

    }

    private void writeResponseLineAndHeaders(Response res, DataOutputStream dos, File file) {
        if (method.equals("GET"))
            res.response200Header(dos, file.length());
        else if (method.equals("POST"))
            res.response302Header(dos, file.length());
    }

    private void readBody(Request req) throws IOException {
        int contentLength = Integer.parseInt(req.getHeader("Content-Length"));
        String body = IOUtils.readData(req.getBr(), contentLength);
        req.setParameter(body);
    }

    protected abstract void doGet(Request req, Response res) throws ServletException, IOException;

    protected abstract void doPost(Request req, Response res) throws ServletException, IOException;
}
