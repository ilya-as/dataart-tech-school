package com.company.jdbc.db;

import com.company.jdbc.model.User;

import com.company.jdbc.utils.PoolDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreSQL implements AutoCloseable {
    private final static Logger LOG = LogManager.getLogger(StoreSQL.class);
    private final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id  serial primary key not null,  " +
            "name TEXT,email TEXT)";
    private final String QUERY_ADD_USER = "INSERT INTO users (name, email) VALUES (?, ?)";
    private final String QUERY_UPDATE_USER = "UPDATE users SET name=?, email=? WHERE id=?";
    private final String QUERY_GET_ALL = "SELECT * FROM users";
    private final String QUERY_DELETE_USER = "DELETE FROM users WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private Connection connection;

    public StoreSQL(Connection connection) {
        this.connection = connection;
        prepareDB();
    }

    public StoreSQL() {
        try {
            this.connection = PoolDataSource.getConnection();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        prepareDB();
    }

    private void prepareDB() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(QUERY_CREATE_TABLE);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public User add(User user) {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    public void update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void delete(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_USER)) {
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL);
            while (resultSet.next()) {
                users.add(userBuilder(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    public User findById(int userId) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = userBuilder(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User userBuilder(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }

    @Override
    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
