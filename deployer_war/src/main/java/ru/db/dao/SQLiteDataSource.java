package ru.db.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class SQLiteDataSource {

    private static final Logger logger = Logger.getLogger(SQLiteDataSource.class);
    private static volatile DataSource dataSource;

    private SQLiteDataSource() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (SQLiteDataSource.class) {
                if (dataSource == null) {
                    InitialContext ic = null;
                    try {
                        ic = new InitialContext();
                        dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/SQLiteDS");
                    } catch (NamingException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return dataSource;
    }

}
