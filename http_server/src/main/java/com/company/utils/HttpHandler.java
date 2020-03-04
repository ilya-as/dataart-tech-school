package com.company.utils;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import com.company.request.Request;
import com.company.responce.Response;
import com.company.session.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;

public class HttpHandler {
    private static final Logger LOG = Logger.getLogger(HttpHandler.class.getName());
    private final int STATUS_OK = 200;
    private final String MESSAGE_STATUS_OK = "OK";
    private final int STATUS_NOT_FOUND = 404;
    private final String MESSAGE_STATUS_NOT_FOUND = "Not found";
    private final String HTTP_PROTOCOL = "HTTP/1.1";
    private final String HEADER_CONTENT_TYPE = "Content-Type";
    private final String VALUE_CONTENT_TYPE = "text/html; charset=UTF-8";
    private final String HEADER_DATE = "Date";
    private final String SESSION_LOG_EXIST_USER = "Connected user with UserID=%s.Last visit was %s";
    private final String SESSION_LOG_NEW_USER = "Connected new user with UserID=%s.Date first visit: %s";
    private final Map<String, Session> sessionContainer;

    public HttpHandler(Map<String, Session> sessionContainer) {
        this.sessionContainer = sessionContainer;
    }

    public void handle(Request request, Response response) {
        InputStream fileInputStream = null;
        String pathToFile = request.getFullPatch();
        if (ServerUtils.isFileExist(pathToFile)) {
            try {
                fileInputStream = new FileInputStream(pathToFile);
            } catch (FileNotFoundException e) {
                LOG.warning(e.getMessage());
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

        String userID = request.getCookie(ServerUtils.USER_ID);
        Session session = getOrCreateNewSession(userID);
        String sessionLog = isUserExist(userID) ? SESSION_LOG_EXIST_USER : SESSION_LOG_NEW_USER;
        LOG.info(String.format(sessionLog, session.getUserID(), session.getLastDate()));
        session.setLastDate(new Date());
        sessionContainer.put(userID, session);
        response.addCookie(ServerUtils.USER_ID, userID);
    }

    private Session getOrCreateNewSession(String userID) {
        if (isUserExist(userID)) {
            return sessionContainer.get(userID);
        }
        return new Session(userID);
    }

    private boolean isUserExist(String userID) {
        return sessionContainer.containsKey(userID);
    }
}
