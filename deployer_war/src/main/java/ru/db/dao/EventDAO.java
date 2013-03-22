package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.db.entities.Event;
import ru.db.entities.Host;

public class EventDAO implements IGenericDAO<Event> {

	private static final String insertSQL = 
			"insert into events(event_time, event, product_name, version, host_id) values(?, ?, ?, ?, ?)";
	private static final String selectSQL = "select * from events";
	private static final String selectJoin = "select * from events inner join hosts on events.host_id = hosts.id";
	
	private Connection conn;
	
	public EventDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Event object) {
		//Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);
			
			pstm = conn.prepareStatement(insertSQL);
			pstm.setDate(1, new java.sql.Date(object.getEventDate().getTime()));
			pstm.setString(2, object.getEventName());
			pstm.setString(3, object.getProductName());
			pstm.setString(4, object.getVersion());
			pstm.setInt(5, object.getHost().getId());
			pstm.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm!=null) {
					pstm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Event> selectALL() {
		//Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Event> eventsList = new ArrayList<Event>(); 
		try {
			conn = SQLiteDataSource.getDataSource().getConnection();
			conn.setAutoCommit(true);
			
			stm = conn.createStatement();
			rs = stm.executeQuery(selectJoin);

			while (rs.next()) {
				Event event = new Event();
				event.setEventDate(rs.getDate("event_time"));
				event.setEventName(rs.getString("event"));
				event.setProductName(rs.getString("product_name"));
				event.setVersion(rs.getString("version"));
				Host host = new Host();
				host.setId(rs.getInt("id"));
				host.setHostName(rs.getString("host_name"));
				host.setProfile(rs.getString("profile"));
				event.setHost(host);
				
				eventsList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!=null) {
					rs.close();
				}
				if (stm!=null) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return eventsList;
	}

}
