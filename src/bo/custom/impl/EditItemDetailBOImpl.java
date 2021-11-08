package bo.custom.impl;

import bo.custom.EditItemDetailBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import entity.Item;
import dto.ItemDTO;

import java.sql.SQLException;

public class EditItemDetailBOImpl implements EditItemDetailBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);

    public boolean updateItem(ItemDTO itemDTO, String actualId) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getPackSize(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice(),itemDTO.getDiscount(),itemDTO.getEveryItem(),itemDTO.getMaxDiscount()),actualId);
    }
}
