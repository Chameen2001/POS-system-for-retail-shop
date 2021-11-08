package bo.custom.impl;

import bo.custom.AddNewItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import entity.Item;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddNewItemBOImpl implements AddNewItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS =new ArrayList<>();
        for (Item item : itemDAO.getAll()) {
            itemDTOS.add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getQtyOnHand(),item.getUnitPrice(),item.getDiscount(),item.getEveryItem(),item.getMaxDiscount()));
        }
        return itemDTOS;
    }

    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getPackSize(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice(),itemDTO.getDiscount(),itemDTO.getEveryItem(),itemDTO.getMaxDiscount()));
    }
}
