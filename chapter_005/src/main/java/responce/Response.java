package responce;

import utils.ServerUtils;

import java.io.InputStream;
import java.util.HashMap;

public class Response {
    private String protocol;
    private int status;
    private String statusMessage;
    private HashMap<String, String> metadata = new HashMap<>();
    private InputStream resource;

    public Response() {
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
        buffer.append("Set-Cookie: userID=14");
        buffer.append(ServerUtils.REQUEST_HEADERS_END_LINE);
        return buffer.toString();
    }
}
