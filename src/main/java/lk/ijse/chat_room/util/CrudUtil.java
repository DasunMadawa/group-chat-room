package lk.ijse.chat_room.util;

import lk.ijse.chat_room.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql , Object... args) throws SQLException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for(int i = 0 ; i < args.length ; i++ ){
            ps.setObject((i+1) , args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")){
            return (T) ps.executeQuery();
        }else {
            return (T)(Boolean) (ps.executeUpdate() > 0);
        }

    }

}
