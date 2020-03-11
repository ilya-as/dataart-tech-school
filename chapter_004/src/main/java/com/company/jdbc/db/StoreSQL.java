package com.company.jdbc.db;

import com.company.jdbc.model.User;

import com.company.jdbc.utils.PoolConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreSQL {
    private final PoolConnectionBuilder builder = new PoolConnectionBuilder();
    private final static Logger LOG = LogManager.getLogger(StoreSQL.class);
    private final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id  serial primary key not null,  " +
            "name TEXT,email TEXT)";
    private final String QUERY_ADD_USER = "INSERT INTO users (name, email) VALUES (?, ?)";
    private final String QUERY_UPDATE_USER = "UPDATE users SET name=?, email=? WHERE id=?";
    private final String QUERY_GET_ALL = "SELECT * FROM users";
    private final String QUERY_DELETE_USER = "DELETE FROM users WHERE id = ?";

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    public StoreSQL() {
        prepareDB();
    }

    private void prepareDB() {
        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(QUERY_CREATE_TABLE);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void add(User user) {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(QUERY_ADD_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void update(User user) {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(QUERY_UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void delete(int userId) {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(QUERY_DELETE_USER)) {
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = getConnection();
             Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY_GET_ALL);
            while (resultSet.next()) {
                User currentUser = new User();
                currentUser.setId(resultSet.getInt("id"));
                currentUser.setName(resultSet.getString("name"));
                currentUser.setEmail(resultSet.getString("email"));
                users.add(currentUser);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }
}
