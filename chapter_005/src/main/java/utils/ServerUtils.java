package utils;

import java.io.*;
import java.util.*;

public final class ServerUtils {
    public static final String REQUEST_HEADERS_END_LINE = "\r\n";
    public static final String REGEX_PATTERN_SPACE = " ";
    public static final String REGEX_PATTERN_COLON_SPACE = ": ";
    private static final String REGEX_PATTERN_SEMICOLON_SPACE = "; ";

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

    public static HashMap<String,String> getCookieFromHeader(String header) {
        HashMap<String, String> cookie = new HashMap<>();
        //header.split(REGEX_PATTERN_SEMICOLON_SPACE)
        return cookie;
    }

    public static boolean isFileExist(String pathToFile) {
        File file = new File(pathToFile);
        return file.exists() && file.isFile();
    }
}
