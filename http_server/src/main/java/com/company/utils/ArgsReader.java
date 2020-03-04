package com.company.utils;

import java.util.logging.Logger;

public class ArgsReader {
    private String directory;
    private int port;
    private String[] args;
    private static final Logger LOG = Logger.getLogger(ArgsReader.class.getName());

    public ArgsReader(String[] args) {
        this.args = args;
        parseOptions();
    }

    public String getDirectory() {
        return directory;
    }

    public int getPort() {
        return port;
    }

    private void parseOptions() {
        if (args.length != 2) {
            System.out.println("Для старта сервера требуется 2 аргумента: 1) порт сервера 2) путь к папке с ресурсами");
        } else {
            directory = args[0].trim();
            port = Integer.parseInt(args[1]);
        }
    }
}
