package lk.ijse.chat_room.dao;

import java.sql.SQLException;

public interface CrudDAO <T> extends SuperDAO {
    public T search(String id) throws SQLException;

}
