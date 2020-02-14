package request;

import utils.ServerUtils;

import java.util.*;

import java.io.IOException;
import java.io.InputStream;

public class RequestReader {
    private static final int HEADERS_KEY_ARRAY_INDEX = 0;
    private static final int RESOURCE_METHOD_ARRAY_INDEX = 1;
    private static final int RESOURCE_START_READ_INDEX = 1;
    private static final int HEADERS_VALUE_ARRAY_INDEX = 1;
    private static final int REGEX_PATTERN_METHOD_INDEX = 0;
    private static final String REGEX_PATTERN_SLASH = "/";
    private static final String REGEX_PATTERN_BACK_SLASH = "\\\\";
    private static final String REGEX_PATTERN_EMPTY_STRING = "";
    private static final String REGEX_PATTERN_COOKIE_STRING = "Cookie";

    public Request readRequest(InputStream inputStream, String pathToFolder) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        String firstLine = ServerUtils.readLine(inputStream);
        if (firstLine.equals(REGEX_PATTERN_EMPTY_STRING)) {
            return new Request();
        }
        String headerLine = ServerUtils.readLine(inputStream);
        while (!headerLine.equals(ServerUtils.REQUEST_HEADERS_END_LINE)) {
            String[] headerArray = headerLine.split(ServerUtils.REGEX_PATTERN_COLON_SPACE);
            metadata.put(headerArray[HEADERS_KEY_ARRAY_INDEX], headerArray[HEADERS_VALUE_ARRAY_INDEX].trim());
            headerLine = ServerUtils.readLine(inputStream);
        }
        String[] firstLineArray = firstLine.split(ServerUtils.REGEX_PATTERN_SPACE);
        String method = firstLineArray[REGEX_PATTERN_METHOD_INDEX];
        String resource = firstLineArray[RESOURCE_METHOD_ARRAY_INDEX].substring(RESOURCE_START_READ_INDEX);
        resource = resource.replaceAll(REGEX_PATTERN_SLASH, REGEX_PATTERN_BACK_SLASH);
        Map<String, String> cookie = ServerUtils.getCookieFromHeader(metadata.get(REGEX_PATTERN_COOKIE_STRING));
        return new Request(resource, method, inputStream, metadata, cookie, pathToFolder);
    }
}
