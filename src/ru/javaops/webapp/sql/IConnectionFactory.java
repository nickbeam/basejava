package ru.javaops.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactory {
    Connection getConnection() throws SQLException;
}
