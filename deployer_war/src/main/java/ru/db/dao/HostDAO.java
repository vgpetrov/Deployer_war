package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.db.entities.Host;

public class HostDAO implements IGenericDAO<Host> {

	private static final String insertSQL = "insert into hosts(id, host_name, profile) values(?, ?, ?)";
	private static final String selectSQL = "select * from hosts";
	private static final String selectSQLById = "select * from hosts where id = ?";
	
	private Connection conn;
	
	public HostDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Host object) {
		//Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);

			pstm = conn.prepareStatement(insertSQL);
			pstm.setInt(1, object.getId());
			pstm.setString(2, object.getHostName());
			pstm.setString(3, object.getProfile());
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

	public List<Host> selectALL() {
		//Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Host> hostList = new ArrayList<Host>();
		try {
			//conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);

			stm = conn.createStatement();
			rs = stm.executeQuery(selectSQL);

			while (rs.next()) {
				Host host = new Host(rs.getInt("id"),
						rs.getString("host_name"), rs.getString("profile"));
				hostList.add(host);
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
	
	public Host selectById(Integer id) {
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
		return host;
	}

}
