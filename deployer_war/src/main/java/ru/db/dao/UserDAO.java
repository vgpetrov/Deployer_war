package ru.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ru.db.entities.User;

public class UserDAO implements IGenericDAO<User> {

    private final static String checkUser = "select id from users where login = ? and password = ?";
    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(User object) {
        // TODO some day...
    }

    public List<User> selectALL() {
        // TODO some day...
        return null;
    }

    public Boolean checkUser(User user) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            pstm = conn.prepareStatement(checkUser);
            pstm.setString(1, user.getLogin());
            pstm.setString(2, user.getPassword());
            rs = pstm.executeQuery();
            while (rs.next()) {
                result = true;
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

        return result;
    }

}
