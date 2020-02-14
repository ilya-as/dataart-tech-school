package responce;

import utils.ServerUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String protocol;
    private int status;
    private String statusMessage;
    private InputStream resource;
    private Map<String, String> metadata = new HashMap<>();
    private Map<String, String> cookie = new HashMap<>();

    public Response() {
    }

    public void addCookie(String cookieKey, String cookieValue) {
        cookie.put(cookieKey, cookieValue);
    }

    public void addHeader(String key, String value) {
        metadata.put(key, value);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InputStream getResource() {
        return resource;
    }

    public void setResource(InputStream resource) {
        this.resource = resource;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String buildHeader() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(protocol + ServerUtils.REGEX_PATTERN_SPACE + status
                + ServerUtils.REGEX_PATTERN_SPACE + statusMessage + ServerUtils.REQUEST_HEADERS_END_LINE);
        for (HashMap.Entry entry : metadata.entrySet()) {
            buffer.append(entry.getKey() + ServerUtils.REGEX_PATTERN_COLON_SPACE
                    + entry.getValue() + ServerUtils.REQUEST_HEADERS_END_LINE);
        }
        if (cookie.containsKey(ServerUtils.COOKIE_KEY)) {
            buffer.append(ServerUtils.REGEX_PATTERN_SET_COOKE
                    + ServerUtils.COOKIE_KEY
                    + ServerUtils.REGEX_PATTERN_EQUAL
                    + cookie.get(ServerUtils.COOKIE_KEY));
        }
        buffer.append(ServerUtils.REQUEST_HEADERS_END_LINE);
        return buffer.toString();
    }
}
