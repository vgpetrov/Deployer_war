package ru.db.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SQLiteDataSource {
	
	private static volatile DataSource dataSource;
	
	private SQLiteDataSource(){}
	
    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (SQLiteDataSource.class) {
                if (dataSource == null) {
                	InitialContext ic = null;
                	try {
                		ic = new InitialContext();
                		dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/SQLiteDS");
                	} catch (NamingException e) {
                		e.printStackTrace();
                	}
                }
            }
        }
        return dataSource;
    }

}
