package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.db.entities.Event;
import ru.db.entities.Host;

public class EventDAO implements IGenericDAO<Event> {

    private static final String insertSQL = "insert into events(event_time, event, product_name, version, revision, host_id) values(?, ?, ?, ?, ?, ?)";
    private static final String selectJoin = "select * from events inner join hosts on events.host_id = hosts.id";
    private static final String selectApps = "select max(event_time) event_time,"
            + "event, product_name, version, revision " + "from events where host_id = ? and event = 'install' "
            + "group by product_name";
    private static final String selectAppsHistory = "select event_time, event, product_name, version, revision "
            + "from events where host_id = ? and product_name = ?";
    private static final String selectHistory = "select event_time, event, product_name, version, revision "
            + "from events where host_id = ?";
    
    private static final Logger logger = Logger.getLogger(EventDAO.class);

    private Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Event object) {
        // Connection conn = null;
        PreparedStatement pstm = null;
        try {
            // conn = SQLiteDataSource.getDataSource().getConnection();
            conn.setAutoCommit(true);

            pstm = conn.prepareStatement(insertSQL);
            pstm.setDate(1, new java.sql.Date(object.getEventDate().getTime()));
            pstm.setString(2, object.getEventName());
            pstm.setString(3, object.getProductName());
            pstm.setString(4, object.getVersion());
            pstm.setString(5, object.getRevision());
            pstm.setInt(6, object.getHost().getId());
            pstm.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public List<Event> selectALL() {
        // Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        List<Event> eventsList = new ArrayList<Event>();
        try {
            // conn = SQLiteDataSource.getDataSource().getConnection();
            // conn.setAutoCommit(true);

            stm = conn.createStatement();
            rs = stm.executeQuery(selectJoin);

            while (rs.next()) {
                Event event = new Event();
                event.setEventDate(rs.getDate("event_time"));
                event.setEventName(rs.getString("event"));
                event.setProductName(rs.getString("product_name"));
                event.setVersion(rs.getString("version"));
                event.setRevision(rs.getString("revision"));
                Host host = new Host();
                host.setId(rs.getInt("id"));
                host.setHostName(rs.getString("host_name"));
                host.setProfile(rs.getString("profile"));
                event.setHost(host);

                eventsList.add(event);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return eventsList;
    }

    public List<Event> selectApps(Integer id) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Event> eventsList = new ArrayList<Event>();
        try {

            pstm = conn.prepareStatement(selectApps);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventDate(rs.getDate("event_time"));
                event.setEventName(rs.getString("event"));
                event.setProductName(rs.getString("product_name"));
                event.setVersion(rs.getString("version"));
                event.setRevision(rs.getString("revision"));
                event.setHost(null);

                eventsList.add(event);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return eventsList;
    }

    public List<Event> selectAppsHistory(Integer id, String appName) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Event> eventsList = new ArrayList<Event>();
        try {

            pstm = conn.prepareStatement(selectAppsHistory);
            pstm.setInt(1, id);
            pstm.setString(2, appName);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventDate(rs.getDate("event_time"));
                event.setEventName(rs.getString("event"));
                event.setProductName(rs.getString("product_name"));
                event.setVersion(rs.getString("version"));
                event.setRevision(rs.getString("revision"));
                event.setHost(null);

                eventsList.add(event);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return eventsList;
    }

    public List<Event> selectHistory(Integer id) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Event> eventsList = new ArrayList<Event>();
        try {

            pstm = conn.prepareStatement(selectHistory);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventDate(rs.getDate("event_time"));
                event.setEventName(rs.getString("event"));
                event.setProductName(rs.getString("product_name"));
                event.setVersion(rs.getString("version"));
                event.setRevision(rs.getString("revision"));
                event.setHost(null);

                eventsList.add(event);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return eventsList;
    }

}
