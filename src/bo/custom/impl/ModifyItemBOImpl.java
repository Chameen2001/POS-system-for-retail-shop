package bo.custom.impl;

import bo.custom.ModifyItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import entity.Item;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyItemBOImpl implements ModifyItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);

    @Override
    public ArrayList<ItemDTO> getItemLike(String itemId) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemDAO.getItemLike(itemId)) {
            itemDTOS.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount()));
        }
        return itemDTOS;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemDAO.getAll()) {
            itemDTOS.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount()));
        }
        return itemDTOS;
    }
}
