package com.company.request;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class Request {
    private String resource;
    private String method;
    private InputStream body;
    private String pathToFolder;
    private Map<String, String> metadata;
    private Map<String, String> cookie;

    public Request() {
    }

    public Request(String resource, String method, InputStream body, Map<String, String> metadata,
                   Map<String, String> cookie, String pathToFolder) {
        this.resource = resource;
        this.method = method;
        this.body = body;
        this.metadata = metadata;
        this.cookie = cookie;
        this.pathToFolder = pathToFolder;
    }

    public String getResource() {
        return resource;
    }

    public String getPathToFolder() {
        return pathToFolder;
    }

    public String getCookie(String cookieKey) {
        return cookie.get(cookieKey);
    }

    public String getFullPatch() {
        File fullPatch = new File(new File(pathToFolder), resource);
        return fullPatch.getAbsolutePath();
    }
}
