package utils;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private String fileProperties;
    private String pathToFolder;
    private int serverPort;
    private final String PROPERTY_PATH_TO_FOLDER = "PATH_TO_FOLDER";
    private final String PROPERTY_SERVER_PORT = "SERVER_PORT";
    private final String MESSAGE_ERROR_PORT = "Ошибка чтения значения порта сервера!";
    private final String MESSAGE_ERROR_PROPERTY_FILE = "Ошибка чтения файла настроек %s не обнаружен!";
    private static final Logger LOG = Logger.getLogger(PropertyReader.class.getName());

    public PropertyReader(String fileProperties) {
        this.fileProperties = fileProperties;
        readProperty();
    }

    public String getPathToFolder() {
        return pathToFolder;
    }

    public int getServerPort() {
        return serverPort;
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
        } catch (IOException e) {
            LOG.log(Level.WARNING, String.format(MESSAGE_ERROR_PROPERTY_FILE, fileProperties));
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, MESSAGE_ERROR_PORT);
        }
    }
}
