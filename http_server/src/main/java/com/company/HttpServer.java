package com.company;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.company.request.Request;
import com.company.request.RequestReader;
import com.company.responce.Response;
import com.company.responce.ResponseWriter;
import com.company.session.Session;
import com.company.utils.HttpHandler;

public class HttpServer {
    private final String pathToFolder;
    private final int serverPort;
    private final int bufferSize;
    private static final Logger LOG = Logger.getLogger(HttpServer.class.getName());
    private volatile Map<String, Session> sessionContainer = new HashMap<>();
    private final String MESSAGE_SERVER_STARTED = "Server started on port: ";
    private final String MESSAGE_GOT_CONNECTION = "Got connection";
    private final String MESSAGE_CONNECTION_CLOSED = "Connection closed";
    private final String SERVER_START_ERROR = "Server start error: %s";
    private final String CLIENT_CONNECTION_ERROR = "Client connection error: %s";

    public HttpServer(String pathToFolder, int serverPort, int bufferSize) {
        this.pathToFolder = pathToFolder;
        this.serverPort = serverPort;
        this.bufferSize = bufferSize;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            LOG.info(MESSAGE_SERVER_STARTED + serverSocket.getLocalPort());
            int poolSize = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
            for (int index = 0; index < poolSize; index++) {
                executorService.execute(new Thread(() -> {
                    while (true) {
                        try (Socket socket = serverSocket.accept()) {
                            LOG.info(MESSAGE_GOT_CONNECTION);
                            RequestReader requestReader = new RequestReader();
                            Request request = requestReader.readRequest(socket.getInputStream(), pathToFolder);
                            Response response = new Response();
                            HttpHandler httpHandler = new HttpHandler(sessionContainer);
                            httpHandler.handle(request, response);
                            ResponseWriter responseWriter = new ResponseWriter();
                            responseWriter.write(socket.getOutputStream(), response, bufferSize);
                            LOG.info(MESSAGE_CONNECTION_CLOSED);
                        } catch (IOException e) {
                            LOG.warning(String.format(SERVER_START_ERROR, e.getMessage()));
                        }
                    }
                }));
            }
            Thread.currentThread().join();
        } catch (IOException e) {
            LOG.warning(String.format(CLIENT_CONNECTION_ERROR, e.getMessage()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
