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

    private static final String insertSQL = "insert into hosts(host_name, profile, web_port, admin_port) values(?, ?, ?, ?)";
    private static final String selectSQL = "select * from hosts";
    private static final String selectSQLByNameAndProfile = "select * from hosts where host_name = ? and profile = ?";

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
                Host host = new Host(rs.getInt("id"), rs.getString("host_name"), rs.getString("profile"), rs.getInt("web_port"), rs.getInt("admin_port"));
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
                host = new Host(rs.getInt("id"), rs.getString("host_name"), rs.getString("profile"), rs.getInt("web_port"), rs.getInt("admin_port"));
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
