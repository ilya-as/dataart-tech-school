package utils;

import java.io.*;
import java.util.*;

public final class ServerUtils {
    public static final String REQUEST_HEADERS_END_LINE = "\r\n";
    public static final String REGEX_PATTERN_SPACE = " ";
    public static final String REGEX_PATTERN_COLON_SPACE = ": ";
    public static final String REGEX_PATTERN_SET_COOKE = "Set-Cookie: ";
    public static final String COOKIE_KEY = "user_id";
    private static final String REGEX_PATTERN_SEMICOLON_SPACE = "; ";
    public static final String REGEX_PATTERN_EQUAL = "=";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;

    private ServerUtils() {
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int symbol = inputStream.read();
        while (symbol != -1 && (char) symbol != '\n') {
            stringBuilder.append((char) symbol);
            symbol = inputStream.read();
        }
        if (symbol != -1) {
            stringBuilder.append((char) symbol);
        }
        return stringBuilder.toString();
    }

    public static Map<String, String> getCookieFromHeader(String header) {
        Map<String, String> cookie = new HashMap<>();
        if (header == null) {
            return cookie;
        }
        Arrays.stream(header.split(REGEX_PATTERN_SEMICOLON_SPACE)).forEach(element -> {
            String[] elemArray = element.split(REGEX_PATTERN_EQUAL);
            if (elemArray.length == 2) {
                cookie.put(elemArray[COOKIE_KEY_INDEX], elemArray[COOKIE_VALUE_INDEX]);
            }
        });
        return cookie;
    }

    public static boolean isFileExist(String pathToFile) {
        File file = new File(pathToFile);
        return file.exists() && file.isFile();
    }
}
