package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM Item");
        ArrayList<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Integer.parseInt(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Integer.parseInt(resultSet.getString(7)),
                    Double.parseDouble(resultSet.getString(8))
            ));
        }
        return items;

    }

    @Override
    public ArrayList<String> getAllItemsId() throws SQLException, ClassNotFoundException {


        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT itemCode FROM Item");
        ArrayList<String> itemIds = new ArrayList<>();
        while (resultSet.next()) {
            itemIds.add(resultSet.getString(1));
        }
        return itemIds;

    }

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?,?,?,?)", item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount());

    }

    @Override
    public Item get(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM Item WHERE itemCode=?",id);
        Item item = null;
        if (resultSet.next()) {
            item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Integer.parseInt(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Integer.parseInt(resultSet.getString(7)),
                    Double.parseDouble(resultSet.getString(8))
            );
        }
        return item;

    }

    @Override
    public boolean update(Item item, String actualId) throws SQLException, ClassNotFoundException {

       return (boolean) CrudUtil.execute("UPDATE Item SET itemCode=?, description=?, packSize=?, qtyOnHand=?, unitPrice=?, discount=?, everyItem=?, maxDiscount=? WHERE itemCode=?", item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount(),actualId);


    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return (boolean) CrudUtil.execute("UPDATE Item SET qtyOnHand=? WHERE itemCode=?",item.getQtyOnHand(),item.getItemCode());
    }

    @Override
    public ArrayList<Item> getItemLike(String itemId) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = new ArrayList<>();

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM Item WHERE itemCode LIKE ?",itemId+"%");
        while (resultSet.next()) {
            items.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Integer.parseInt(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Integer.parseInt(resultSet.getString(7)),
                    Double.parseDouble(resultSet.getString(8))
            ));
        }
        return items;

    }

    @Override
    public boolean delete(String itemId) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("DELETE FROM Item WHERE itemCode=?", itemId);

    }
}
