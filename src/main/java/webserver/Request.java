package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger log = LoggerFactory.getLogger(Request.class);
    private static final String DEFAULT_PATH = "./webapp";
    private static final String DEFAULT_ROOT = "/index.html";

    private Map<String, String> headers;
    private Map<String, String> parameter;
    private String url;
    private String path;
    private String method;
    private BufferedReader br;


    public Request(BufferedReader br) {
        this.br = br;
        headers = new HashMap<>();

        try {
            getRequestLine(br);
            putHeaders();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void getRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        log.info(requestLine);

        String[] split = requestLine.split(" ");
        method = split[0];
        url = split[1];

        int index = url.indexOf("?");
        if(index!=-1) {
            path = url.substring(0, index);
            parameter = HttpRequestUtils.parseQueryString(url.substring(index+1));
            return;
        }
        path = url;
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

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public String getMethod() {
        return method;
    }
}
