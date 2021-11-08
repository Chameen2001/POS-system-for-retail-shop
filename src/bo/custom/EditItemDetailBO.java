package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;

public interface EditItemDetailBO extends SuperBO {
    boolean updateItem(ItemDTO itemDTO, String actualId) throws SQLException, ClassNotFoundException;
}
