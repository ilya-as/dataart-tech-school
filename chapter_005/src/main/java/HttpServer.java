import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import request.Request;
import request.RequestReader;
import responce.Response;
import responce.ResponseWriter;
import session.Session;
import utils.HttpHandler;

public class HttpServer {
    private String pathToFolder;
    private int serverPort;
    private static final Logger LOG = Logger.getLogger(HttpServer.class.getName());
    private Map<String, Session> sessionContainer = new HashMap<>();
    private final String MESSAGE_SERVER_STARTED = "Server started on port: ";
    private final String MESSAGE_GOT_CONNECTION = "Got connection";
    private final String MESSAGE_CONNECTION_CLOSED = "Connection closed";

    public HttpServer(String pathToFolder, int serverPort) {
        this.pathToFolder = pathToFolder;
        this.serverPort = serverPort;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            LOG.info(MESSAGE_SERVER_STARTED + serverSocket.getLocalPort());
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    LOG.info(MESSAGE_GOT_CONNECTION);
                    RequestReader requestReader = new RequestReader();
                    Request request = requestReader.readRequest(socket.getInputStream(), pathToFolder);
                    Response response = new Response();
                    HttpHandler httpHandler = new HttpHandler(sessionContainer);
                    httpHandler.handle(request, response);
                    ResponseWriter responseWriter = new ResponseWriter();
                    responseWriter.write(socket.getOutputStream(), response);
                    LOG.info(MESSAGE_CONNECTION_CLOSED);
                }
            }
        } catch (IOException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }
}
