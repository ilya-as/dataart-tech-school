package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.Request;
import responce.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;

public class HttpHandler {
    private static final Logger LOG = LogManager.getLogger(HttpHandler.class.getName());
    private final int STATUS_OK = 200;
    private final String MESSAGE_STATUS_OK = "OK";
    private final int STATUS_NOT_FOUND = 404;
    private final String MESSAGE_STATUS_NOT_FOUND = "Not found";
    private final String HTTP_PROTOCOL = "HTTP/1.1";
    private final String HEADER_CONTENT_TYPE = "Content-Type";
    private final String VALUE_CONTENT_TYPE = "text/html; charset=UTF-8";
    private final String HEADER_DATE = "Date";

    public HttpHandler() {
    }

    public void handle(Request request, Response response) {
        InputStream fileInputStream = null;
        String pathToFile = request.getFullPatch();
        if (ServerUtils.isFileExist(pathToFile)) {
            try {
                fileInputStream = new FileInputStream(pathToFile);
            } catch (FileNotFoundException e) {
                LOG.warn(e.getMessage());
            }
        }
        if (fileInputStream == null) {
            response.setStatus(STATUS_NOT_FOUND);
            response.setStatusMessage(MESSAGE_STATUS_NOT_FOUND);
        } else {
            response.setStatus(STATUS_OK);
            response.setStatusMessage(MESSAGE_STATUS_OK);
            response.setResource(fileInputStream);
        }
        response.setProtocol(HTTP_PROTOCOL);
        response.addHeader(HEADER_CONTENT_TYPE, VALUE_CONTENT_TYPE);
        response.addHeader(HEADER_DATE, Instant.now().toString());
         if (request.getCookie(ServerUtils.COOKIE_KEY) == null) {
            response.addCookie(ServerUtils.COOKIE_KEY, UUID.randomUUID().toString());
        }
    }
}