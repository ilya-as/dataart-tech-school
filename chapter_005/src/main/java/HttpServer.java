import java.util.UUID;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.Request;
import request.RequestReader;
import responce.Response;
import responce.ResponseWriter;
import utils.HttpHandler;

public class HttpServer {
    private String pathToFolder;
    private int serverPort;
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    private final String MESSAGE_SERVER_STARTED = "Server started on port: ";
    private final String MESSAGE_GOT_CONNECTION = "Got connection %s";
    private final String MESSAGE_CONNECTION_CLOSED = "Connection closed %s";

    public HttpServer(String pathToFolder, int serverPort) {
        this.pathToFolder = pathToFolder;
        this.serverPort = serverPort;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            LOG.info(MESSAGE_SERVER_STARTED +  serverSocket.getLocalPort());
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    UUID connectionId = UUID.randomUUID();
                    LOG.info(String.format(MESSAGE_GOT_CONNECTION, connectionId));
                    RequestReader requestReader = new RequestReader();
                    Request request = requestReader.readRequest(socket.getInputStream(),pathToFolder);
                    Response response = new Response();
                    HttpHandler httpHandler = new HttpHandler();
                    httpHandler.handle(request, response);
                    ResponseWriter responseWriter = new ResponseWriter();
                    responseWriter.write(socket.getOutputStream(), response);
                    LOG.info(String.format(MESSAGE_CONNECTION_CLOSED, connectionId));
                }
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
    }
}
