import utils.PropertyReader;

public class Main {
    private static final String FileProperties = "app.properties";

    public static void main(String[] args) {
        PropertyReader propertyReader = new PropertyReader(FileProperties);
        HttpServer server = new HttpServer(propertyReader.getPathToFolder(), propertyReader.getServerPort());
        server.startServer();
    }
}
