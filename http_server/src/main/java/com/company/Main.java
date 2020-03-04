package com.company;

import com.company.utils.ArgsReader;
import com.company.utils.PropertyReader;

public class Main {
    public static void main(String[] args) {
        ArgsReader argsReader = new ArgsReader(args);
        PropertyReader propertyReader = new PropertyReader();
        HttpServer server = new HttpServer(argsReader.getDirectory(), argsReader.getPort(),
                propertyReader.getBufferSize());
        server.startServer();
    }
}
