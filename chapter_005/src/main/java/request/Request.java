package request;

import java.util.UUID;
import java.io.InputStream;
import java.util.HashMap;

public class Request {
    private String resource;
    private String method;
    private InputStream body;
    private String pathToFolder;
    HashMap<String, String> metadata;
    HashMap<String, String> cookie;

    public Request() {
    }

    public Request(String resource, String method, InputStream body, HashMap<String, String> metadata, String pathToFolder) {
        this.resource = resource;
        this.method = method;
        this.body = body;
        this.metadata = metadata;
        this.pathToFolder = pathToFolder;
    }

    public String getResource() {
        return resource;
    }

    public void  getConnectionUUID(){
        //if cookie.containsKey("")UUID
    }

    public String getFullPatch() {
        return pathToFolder + resource;
    }
}
