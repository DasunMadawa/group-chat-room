package lk.ijse.chat_room.bo.custom;

import lk.ijse.chat_room.bo.SuperBO;
import lk.ijse.chat_room.dto.UserDTO;
import lk.ijse.chat_room.entity.User;

public interface UserBO extends SuperBO {
    public boolean checkUser(UserDTO userDTO);

}
