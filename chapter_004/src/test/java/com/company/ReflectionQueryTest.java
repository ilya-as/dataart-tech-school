package com.company;

import com.company.jdbc.db.ReflectionQuery;
import com.company.jdbc.db.StoreSQL;
import com.company.jdbc.model.User;
import com.company.jdbc.utils.PoolDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReflectionQueryTest {
    private Connection connection;
    private StoreSQL testStore;
    private final String QUERY_CLEAR_TEST_USER = "DELETE FROM users WHERE name = 'test'";
    private User first = new User("test", "test@mail.ru");
    private ReflectionQuery reflectionQuery;

    @Before
    public void setUp() throws SQLException {
        this.connection = PoolDataSource.getConnection();
        this.connection.setAutoCommit(false);
        this.testStore = new StoreSQL();
        this.reflectionQuery = new ReflectionQuery(User.class);
    }

    @Test
    public void findById() {
        int id = testStore.add(first).getId();
        assertThat(reflectionQuery.getById(id), is(first));
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
}
