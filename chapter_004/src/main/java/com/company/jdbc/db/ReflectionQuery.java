package com.company.jdbc.db;

import com.company.jdbc.utils.PoolDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionQuery {

    private final Class clazz;

    public ReflectionQuery(Class clazz) {
        this.clazz = clazz;
    }

    public Object getById(int userId) {
        Object object = null;
        try (Connection connection = PoolDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(buildQuery())) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                object = objectBuilder(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    private String buildQuery() {
        StringBuffer stringBuffer = new StringBuffer("SELECT ");
        Field[] fields = clazz.getDeclaredFields();
        String prefix = "";
        for (Field field : fields) {
            stringBuffer.append(prefix);
            prefix = ", ";
            stringBuffer.append(field.getName());
        }
        stringBuffer.append(" FROM ");
        stringBuffer.append(clazz.getSimpleName().toLowerCase());
        stringBuffer.append("s");
        stringBuffer.append(" WHERE id=?");
        return stringBuffer.toString();
    }

    private Object objectBuilder(ResultSet resultSet) throws SQLException, IllegalAccessException {
        Object object = null;
        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == int.class) {
                field.set(object, resultSet.getInt(field.getName()));
            }
            if (field.getType() == String.class) {
                field.set(object, resultSet.getString(field.getName()));
            }
        }
        return object;
    }
}
