package ru.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnector {
	
	private Connection connection;

	public SQLiteConnector() {
		try {
			this.connection = SQLiteDataSource.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
