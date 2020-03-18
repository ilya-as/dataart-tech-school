package com.company;

import com.company.jdbc.db.StoreSQL;
import com.company.jdbc.model.User;

import com.company.jdbc.utils.PoolDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreSQLTest {
    private Connection connection;
    private StoreSQL testStore;
    private User first = new User("test", "test@mail.ru");
    private User second = new User("test", "test@mail.ru");
    private final String QUERY_GET_USER_TEST_BY_NAME = "SELECT * FROM users WHERE name = 'test'";
    private final String QUERY_SELECT_USER_ID = "SELECT id FROM users WHERE name = 'test'";
    private final String QUERY_CLEAR_TEST_USER = "DELETE FROM users WHERE name = 'test'";

    @Before
    public void setUp() throws SQLException {
        this.connection = PoolDataSource.getConnection();
        this.connection.setAutoCommit(false);
        this.testStore = new StoreSQL();
    }

    @After
    public void clear() throws Exception {
        try (Statement st = this.connection.createStatement()) {
            st.execute(QUERY_CLEAR_TEST_USER);
        }
        this.connection.rollback();
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
        int id = testStore.add(first).getId();
        first.setEmail("test2@mail.ru");
        testStore.update(first);
        assertThat(testStore.findById(id).getEmail(), is("test2@mail.ru"));
    }

    @Test
    public void delete() throws SQLException {
        int id = testStore.add(first).getId();
        testStore.delete(id);
        assertThat(testStore.findById(id), nullValue());
    }

    @Test
    public void findById() throws SQLException {
        int id = testStore.add(first).getId();
        assertThat(testStore.findById(id).getName(), is(first.getName()));
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

