package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RemoveItemBO extends SuperBO {

    ArrayList<ItemDTO> getItemLike(String itemId) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException;

}
