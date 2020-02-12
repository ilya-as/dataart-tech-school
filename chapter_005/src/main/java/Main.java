import utils.PropertyReader;

public class Main {

    public static void main(String[] args) {
        PropertyReader propertyReader = new PropertyReader();
        HttpServer server = new HttpServer(propertyReader.getPathToFolder(), propertyReader.getServerPort());
        server.startServer();
    }
}
