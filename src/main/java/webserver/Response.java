package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.HttpServlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final Logger log = LoggerFactory.getLogger(Response.class);

    private static Map<String, String> cookies = new HashMap<>();
    private boolean isAddCookie = false;
    private DataOutputStream dos;
    private String responseDispatcher;


    public Response(DataOutputStream dos) {
        this.dos = dos;
    }

    public String getResponseDispatcher() {
        return responseDispatcher;
    }

    public void forward(String responseDispatcher) {
        this.responseDispatcher = responseDispatcher;
    }

    public void response302Header(DataOutputStream dos, long lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: "+responseDispatcher+ "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            writeCookies(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response200Header(DataOutputStream dos, long lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            writeCookies(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response400Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND");
            dos.writeBytes("Content-Type: text/html; charset=UTF-8");
            writeCookies(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void addCookie(String key, String value){
        cookies.put(key,value);
        isAddCookie = true;
    }

    private void writeCookies(DataOutputStream dos) throws IOException {
        if (!isAddCookie)
            return;

        dos.writeBytes("Set-Cookie: ");
        for(String key : cookies.keySet() )
            dos.writeBytes(key+"="+cookies.get(key)+"; ");
        dos.writeBytes("\r\n");
        isAddCookie=false;
    }

    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void sendRedirect(String path) {

    }
}
