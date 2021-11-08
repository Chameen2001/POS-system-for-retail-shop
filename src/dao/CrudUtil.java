package dao;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static Object execute(String sql,Object... objects) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i+1,objects[i]);
        }
        if (sql.split(" ")[0].equalsIgnoreCase("Select")){
            return preparedStatement.executeQuery();
        }else {
            return preparedStatement.executeUpdate()>0;
        }

    }
}
