package com.company;

import com.company.jdbc.db.StoreSQL;
import com.company.jdbc.model.User;

import com.company.jdbc.utils.PoolConnectionBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreSQLTest {
    private PoolConnectionBuilder builder;
    private Connection connection;
    private StoreSQL testStore;
    private User first = new User("test", "test@mail.ru");
    private User second = new User("test", "test@mail.ru");
    private final String QUERY_GET_USER_TEST_BY_NAME = "SELECT * FROM users WHERE name = 'test'";
    private final String QUERY_INSERT_USER_TEST = "INSERT INTO users (name, email) VALUES ('test', 'test@mail.ru')";
    private final String QUERY_DELETE_USER_TEST = "DELETE FROM users WHERE name = 'test'";
    private final String QUERY_SELECT_USER_ID = "SELECT id FROM users WHERE name = 'test'";


    @Before
    public void setUp() throws SQLException {
        this.testStore = new StoreSQL();
        this.builder = new PoolConnectionBuilder();
        this.connection = this.builder.getConnection();
        this.connection.setAutoCommit(false);
    }

    @After
    public void closeConnection() throws Exception {
        connection.rollback();
        if (this.connection != null) {
            this.connection.close();
        }
    }

    @Test
    public void add() throws SQLException {
        this.testStore.add(this.first);
        try (PreparedStatement statement = connection.prepareStatement(QUERY_GET_USER_TEST_BY_NAME)) {
            ResultSet rstSet = statement.executeQuery();
            rstSet.next();
            assertThat(rstSet.getString("name"), is("test"));
            assertThat(rstSet.getString("email"), is("test@mail.ru"));
            assertThat(rstSet.getInt("id") > 0, is(true));
        }
     }

    @Test
    public void update() throws SQLException {
        int userId = 0;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(QUERY_INSERT_USER_TEST);
            ResultSet rstSet = statement.executeQuery(QUERY_GET_USER_TEST_BY_NAME);
            rstSet.next();
            userId = rstSet.getInt("id");
            User userUpdated = new User("test", "test@test.com");
            userUpdated.setId(userId);
            this.testStore.update(userUpdated);
            ResultSet resultSet = statement.executeQuery("SELECT name, email FROM users WHERE id =" + userId);
            assertThat(resultSet.next(), is(true));
            assertThat(resultSet.getString("name"), is("test"));
            assertThat(resultSet.getString("email"), is("test@mail.ru"));
            assertThat(resultSet.next(), is(false));
        }
    }

    @Test
    public void delete() throws SQLException {
        int userId = 0;
        try (Statement statement = connection.createStatement()) {
            statement.execute(QUERY_INSERT_USER_TEST);
            ResultSet resultSet = statement.executeQuery(QUERY_GET_USER_TEST_BY_NAME);
            resultSet.next();
            userId = resultSet.getInt("id");
            this.testStore.delete(userId);
            resultSet = statement.executeQuery("SELECT name, email FROM users WHERE id =" + userId);
            assertThat(resultSet.next(), is(false));
        }
    }

    @Test
    public void getAll() throws SQLException {
        this.testStore.add(first);
        this.testStore.add(second);
        List<User> result = this.testStore.getAll();
        List<User> expect = new ArrayList<>(Arrays.asList(first, second));
        try (Statement st = connection.createStatement();
             ResultSet rstSet = st.executeQuery(QUERY_SELECT_USER_ID)) {
            rstSet.next();
            this.first.setId(rstSet.getInt("id"));
            rstSet.next();
            this.second.setId(rstSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result.containsAll(expect), is(true));
    }
}

