package com.company.jdbc.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder {
    private ComboPooledDataSource dataSource;
    private final String URL = "url";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String DRIVER = "driver";
    private final static Logger LOG = LogManager.getLogger(PoolConnectionBuilder.class);
    private final Config config = new Config();
    private final int MAX_POOL_SIZE = 20;

    public PoolConnectionBuilder() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(config.get(DRIVER));
            dataSource.setJdbcUrl(config.get(URL));
            dataSource.setUser(config.get(USERNAME));
            dataSource.setPassword(config.get(PASSWORD));
            dataSource.setMaxPoolSize(MAX_POOL_SIZE);
        } catch (PropertyVetoException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
