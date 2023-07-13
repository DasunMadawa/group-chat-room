package lk.ijse.chat_room.dao.custom.impl;

import lk.ijse.chat_room.dao.custom.UserDAO;
import lk.ijse.chat_room.entity.User;
import lk.ijse.chat_room.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User search(String userName) throws SQLException {
        String sql = "SELECT * FROM user WHERE userName = ?";
        ResultSet rs = CrudUtil.execute(sql , userName);

        if (rs.next()){
            String password = rs.getString(2);

            return new User(userName , password);
        }
        return null;

    }

}
