package ru.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class SQLiteConnector {

    private static final Logger logger = Logger.getLogger(SQLiteConnector.class);
    private Connection connection;

    public SQLiteConnector() {
        try {
            this.connection = SQLiteDataSource.getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
