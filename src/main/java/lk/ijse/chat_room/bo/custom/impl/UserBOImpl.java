package lk.ijse.chat_room.bo.custom.impl;

import lk.ijse.chat_room.bo.custom.UserBO;
import lk.ijse.chat_room.dao.DAOFactory;
import lk.ijse.chat_room.dao.custom.UserDAO;
import lk.ijse.chat_room.dto.UserDTO;
import lk.ijse.chat_room.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean checkUser(UserDTO userDTO) {
        try {
            User user = userDAO.search(userDTO.getUserName());
            if (user == null){
                return false;
            }

            if ( userDTO.getPassword().equals( user.getPassword() ) ){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
