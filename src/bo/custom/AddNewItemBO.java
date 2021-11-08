package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AddNewItemBO extends SuperBO {
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException ;

    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException ;
}
