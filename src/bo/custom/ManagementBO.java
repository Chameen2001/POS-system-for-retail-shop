package bo.custom;

import bo.SuperBO;
import dto.ItemMovableData;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManagementBO extends SuperBO {
    ArrayList<ItemMovableData> getItemMovableData() throws SQLException, ClassNotFoundException;
}
