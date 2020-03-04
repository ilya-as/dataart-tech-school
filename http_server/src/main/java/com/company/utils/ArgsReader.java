package com.company.utils;

import org.apache.commons.cli.*;

public class ArgsReader {
    private String directory;
    private int port;
    private String[] args;

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
        Options options = new Options();
        options.addRequiredOption("d", "directory", true, "directory path");
        options.addRequiredOption("p", "port", true, "server port");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("d")) {
                directory = line.getOptionValue("d");
                System.out.println(directory);
            } else {
                throw new UnsupportedOperationException("parameter d (directory) is not set");
            }

            if (line.hasOption("p")) {
                port = Integer.parseInt(line.getOptionValue("p"));
                System.out.println(port);
            } else {
                throw new UnsupportedOperationException("parameter p (port) is not set");
            }

        } catch (ParseException exp) {
            System.out.println("Parse exception:" + exp.getMessage());
        }

    }
}
