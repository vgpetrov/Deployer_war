package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ru.db.entities.Host;

public class HostDAO implements IGenericDAO<Host> {

    private static final String insertSQL = "insert into hosts(host_name, profile, web_port, admin_port) values(?, ?, ?, ?)";
    private static final String selectSQL = "select * from hosts";
    private static final String selectSQLByNameAndProfile = "select * from hosts where host_name = ? and profile = ?";

    private static String advancedSelectSQL = "select id, host_name, profile, web_port, admin_port from "
            + "(select hosts.id, hosts.host_name, hosts.profile, hosts.web_port, hosts.admin_port, "
            + "max(events.event_time) event_time, events.version, events.revision, events.product_name "
            + "from hosts inner join events on hosts.id = events.host_id where events.event = 'install' "
            + "group by hosts.id, events.product_name) resTable ";

    private String advancedSelectSQL(String product, String version, String revision) {
        StringBuilder sb = new StringBuilder();
        if (product != null && !product.isEmpty()) {
            sb.append(advancedSelectSQL).append("where resTable.product_name like ? ");
        } else {
            sb.append(advancedSelectSQL).append("where 1=1 ");
        }
        if (version != null && !version.isEmpty()) {
            sb.append("and resTable.version = ? ");
        }
        if (revision != null && !revision.isEmpty()) {
            sb.append("and resTable.revision = ?");
        }
        return sb.toString();
    }

    private static final Logger logger = Logger.getLogger(HostDAO.class);

    private Connection conn;

    public HostDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Host object) {
        // Connection conn = null;
        PreparedStatement pstm = null;
        try {
            // conn = SQLiteDataSource.getDataSource().getConnection();
            conn.setAutoCommit(true);

            pstm = conn.prepareStatement(insertSQL);
            pstm.setString(1, object.getHostName());
            pstm.setString(2, object.getProfile());
            pstm.setInt(3, object.getWebPort());
            pstm.setInt(4, object.getAdminPort());
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

    public List<Host> selectALL() {
        // Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        List<Host> hostList = new ArrayList<Host>();
        try {
            // conn = SQLiteDataSource.getDataSource().getConnection();
            // conn.setAutoCommit(true);

            stm = conn.createStatement();
            rs = stm.executeQuery(selectSQL);

            while (rs.next()) {
                Host host = new Host(rs.getInt("id"), rs.getString("host_name"), rs.getString("profile"),
                        rs.getInt("web_port"), rs.getInt("admin_port"));
                hostList.add(host);
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

        return hostList;
    }

    public Host selectByNameAndProfile(String name, String profile) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Host host = null;
        try {
            // conn.setAutoCommit(true);
            pstm = conn.prepareStatement(selectSQLByNameAndProfile);
            pstm.setString(1, name);
            pstm.setString(2, profile);
            rs = pstm.executeQuery();

            if (rs.next()) {
                host = new Host(rs.getInt("id"), rs.getString("host_name"), rs.getString("profile"),
                        rs.getInt("web_port"), rs.getInt("admin_port"));
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
        return host;
    }

    public List<Host> selectAdvancedSearch(String product, String version, String revision) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Host> hostList = new ArrayList<Host>();
        int i = 1;
        try {
            pstm = conn.prepareStatement(advancedSelectSQL(product, version, revision));
            if (product != null && !product.isEmpty()) {
                pstm.setString(i, product);
                i++;
            }
            if (version != null && !version.isEmpty()) {
                pstm.setString(i, version);
                i++;
            }
            if (revision != null && !revision.isEmpty()) {
                pstm.setString(i, revision);
                i++;
            }
            rs = pstm.executeQuery();

            while (rs.next()) {
                Host host = new Host(rs.getInt("id"), rs.getString("host_name"), rs.getString("profile"),
                        rs.getInt("web_port"), rs.getInt("admin_port"));
                hostList.add(host);
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

        return hostList;
    }

}
