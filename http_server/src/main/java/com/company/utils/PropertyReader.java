package com.company.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.Properties;

public class PropertyReader {
    private String pathToFolder;
    private int bufferSize;
    private int serverPort;
    private static final String fileProperties = "app.properties";
    private final String PROPERTY_PATH_TO_FOLDER = "PATH_TO_FOLDER";
    private final String PROPERTY_SERVER_PORT = "SERVER_PORT";
    private final String PROPERTY_BUFFER_SIZE = "BUFFER_SIZE";
    private final String MESSAGE_ERROR_PORT = "Ошибка чтения значения порта сервера!";
    private final String MESSAGE_ERROR_PROPERTY_FILE = "Ошибка чтения файла настроек %s не обнаружен!";
    private static final Logger LOG = Logger.getLogger(PropertyReader.class.getName());

    public PropertyReader() {
        readProperty();
    }

    public String getPathToFolder() {
        return pathToFolder;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    private void readProperty() {
        File file = new File(getClass().getClassLoader().getResource(fileProperties).getFile());
        Properties prop = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            LogManager.getLogManager().readConfiguration(fileInputStream);
            prop.load(fileInputStream);
            pathToFolder = LogManager.getLogManager().getProperty(PROPERTY_PATH_TO_FOLDER);
            serverPort = Integer.parseInt(LogManager.getLogManager().getProperty(PROPERTY_SERVER_PORT));
            bufferSize = Integer.parseInt(LogManager.getLogManager().getProperty(PROPERTY_BUFFER_SIZE));
        } catch (IOException e) {
            LOG.log(Level.WARNING, String.format(MESSAGE_ERROR_PROPERTY_FILE, fileProperties));
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, MESSAGE_ERROR_PORT);
        }
    }
}
