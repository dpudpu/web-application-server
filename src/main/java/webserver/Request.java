package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger log = LoggerFactory.getLogger(Request.class);

    private Map<String, String> headers;
    private String path;
    private String method;
    private BufferedReader br;


    public Request(BufferedReader br){
        this.br = br;
        headers = new HashMap<>();

        try {
            getRequestLine(br);

            putHeaders();
        }catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void getRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] split = requestLine.split(" ");
        this.method = split[0];
        this.path = split[1];
        log.info(requestLine);
    }

    private void putHeaders() throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals(""))
                break;
            int index = line.indexOf(":");
            if (index != -1) {
                String headerName = line.substring(0, index);
                String headerValue = line.substring(index + 1).trim();
                headers.put(headerName, headerValue);
            }
            log.info(line);
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}
