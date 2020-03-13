package com.company.jdbc.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolDataSource {
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private final static String URL = "url";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    private final static String DRIVER = "driver";
    private final static Logger LOG = LogManager.getLogger(PoolDataSource.class);
    private final static Config config = new Config();
    private final static int MAX_POOL_SIZE = 20;

    static {
        try {
            dataSource.setDriverClass(config.get(DRIVER));
            dataSource.setJdbcUrl(config.get(URL));
            dataSource.setUser(config.get(USERNAME));
            dataSource.setPassword(config.get(PASSWORD));
            dataSource.setMaxPoolSize(MAX_POOL_SIZE);
        } catch (PropertyVetoException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private PoolDataSource() {

    }
}
