package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {

    ArrayList<String> getAllItemsId() throws SQLException, ClassNotFoundException;

    boolean update(Item item, String actualId) throws SQLException, ClassNotFoundException;

    ArrayList<Item> getItemLike(String itemId) throws SQLException, ClassNotFoundException;

}
