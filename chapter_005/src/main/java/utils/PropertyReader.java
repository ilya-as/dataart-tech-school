package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private final String FILE_NAME_PROPERTIES = "app.properties";
    private String pathToFolder;
    private int serverPort;

    public PropertyReader() {
        readProperty();
    }

    public String getPathToFolder() {
        return pathToFolder;
    }

    public int getServerPort() {
        return serverPort;
    }

    private void readProperty() {
        File file = new File(getClass().getClassLoader().getResource(FILE_NAME_PROPERTIES).getFile());
        Properties prop = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            prop.load(fileInputStream);
            pathToFolder = prop.getProperty("PATH_TO_FOLDER");
            serverPort = Integer.parseInt(prop.getProperty("SERVER_PORT"));
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл настроек " + FILE_NAME_PROPERTIES + " не обнаружен!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка чтения значения порта сервера!");
            e.printStackTrace();
        }
    }
}
