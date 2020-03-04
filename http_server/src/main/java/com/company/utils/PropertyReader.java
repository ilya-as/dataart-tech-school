package com.company.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PropertyReader {
    private int bufferSize;
    private static final String fileProperties = "app.properties";
    private final String PROPERTY_BUFFER_SIZE = "BUFFER_SIZE";
    private final String MESSAGE_ERROR_PORT = "Ошибка чтения значения порта сервера!";
    private final String MESSAGE_ERROR_PROPERTY_FILE = "Ошибка чтения файла настроек %s не обнаружен!";
    private static final Logger LOG = Logger.getLogger(PropertyReader.class.getName());

    public PropertyReader() {
        readProperty();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    private void readProperty() {
        try {
            LogManager.getLogManager().readConfiguration(this.getClass().getClassLoader().getResourceAsStream(fileProperties));
            bufferSize = Integer.parseInt(LogManager.getLogManager().getProperty(PROPERTY_BUFFER_SIZE));
        } catch (IOException e) {
            LOG.log(Level.WARNING, String.format(MESSAGE_ERROR_PROPERTY_FILE, fileProperties));
            System.out.println(String.format(MESSAGE_ERROR_PROPERTY_FILE, fileProperties));
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, MESSAGE_ERROR_PORT);
        }
    }
}
