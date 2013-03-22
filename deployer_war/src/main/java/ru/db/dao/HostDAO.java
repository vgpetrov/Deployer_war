package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.db.entities.Host;

public class HostDAO<T> implements IGenericDAO<T> {

	private static final String insertSQL = "insert into hosts(id, host_name, profile) values(?, ?, ?)";
	private static final String selectSQL = "select * from hosts";
	private static final String selectSQLById = "select * from hosts where id = ?";
	
	private Connection conn;
	
	public HostDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(T object) {
		//Connection conn = null;
		PreparedStatement pstm = null;
		Host obj = (Host) object;
		try {
			//conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);

			pstm = conn.prepareStatement(insertSQL);
			pstm.setInt(1, obj.getId());
			pstm.setString(2, obj.getHostName());
			pstm.setString(3, obj.getProfile());
			pstm.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<T> selectALL() {
		//Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		List<T> hostList = new ArrayList<T>();
		try {
			//conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);

			stm = conn.createStatement();
			rs = stm.executeQuery(selectSQL);

			while (rs.next()) {
				Host host = new Host(rs.getInt("id"),
						rs.getString("host_name"), rs.getString("profile"));
				hostList.add((T) host);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return hostList;
	}
	
	public T selectById(Integer id) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Host host = null;
		try {
			conn.setAutoCommit(true);

			pstm = conn.prepareStatement(selectSQLById);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			
			if(rs!=null) {
				rs.next();
				host = new Host(rs.getInt("id"),
						rs.getString("host_name"), rs.getString("profile"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (T) host;
	}

}
