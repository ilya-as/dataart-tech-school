package com.company.utils;

import java.util.logging.Logger;

public class ArgsReader {
    private String directory;
    private int port;
    private String[] args;
    private static final Logger LOG = Logger.getLogger(ArgsReader.class.getName());
    private final int FIRST_ARGUMENT_INDEX = 0;
    private final int SECOND_ARGUMENT_INDEX = 1;
    private final int COUNT_ARGUMENTS = 2;
    private final String ARGUMENTS_COUNT_ERROR = "Для старта сервера требуется 2 аргумента: 1) порт сервера 2)" +
            " путь к папке с ресурсами";

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
        if (args.length != COUNT_ARGUMENTS) {
            System.out.println(ARGUMENTS_COUNT_ERROR);
            LOG.warning(ARGUMENTS_COUNT_ERROR);
        } else {
            directory = args[FIRST_ARGUMENT_INDEX].trim();
            port = Integer.parseInt(args[SECOND_ARGUMENT_INDEX]);
        }
    }
}
