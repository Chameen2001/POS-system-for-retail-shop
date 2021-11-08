package dao.custom.impl;

import db.DbConnection;
import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("INSERT INTO `Order Detail` VALUES (?,?,?,?,?,?,?)", orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getUnitPrice(), orderDetail.getPrice() - orderDetail.getDiscount(), orderDetail.getPercentage());


    }

    @Override
    public ArrayList<OrderDetail> getItemMovableData() throws SQLException, ClassNotFoundException {
        ArrayList<entity.OrderDetail> data = new ArrayList<>();
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT itemCode,SUM(quantity) FROM `Order Detail` GROUP BY itemCode").executeQuery();
        while (resultSet.next()) {
            data.add(new entity.OrderDetail(
                    resultSet.getString(1),
                    Integer.parseInt(resultSet.getString(2))
            ));
        }
        return data;
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("UPDATE `Order Detail` SET quantity = ? , discount = ? , price = ? WHERE orderId=? AND itemCode=?", orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getPrice(), orderDetail.getOrderId(), orderDetail.getItemCode());

    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDetail get(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getPriceFromOrder(String orderId) throws SQLException, ClassNotFoundException {
        double total = 0.0;
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT price FROM `Order Detail` WHERE orderId=?", orderId);
        while (resultSet.next()) {
            total += Double.parseDouble(resultSet.getString(1));
        }
        return total;
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM `Order Detail` WHERE orderId=?", orderId);
        while (resultSet.next()) {
            orderDetails.add(new OrderDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)),
                    Double.parseDouble(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Double.parseDouble(resultSet.getString(7))
            ));
        }
        return orderDetails;
    }

    @Override
    public boolean deleteItem(String orderId, String itemId) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("DELETE FROM `Order Detail` WHERE orderId=? AND itemCode=?", orderId, itemId);

    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM `Order Detail`");
        while (resultSet.next()) {
            orderDetails.add(new OrderDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)),
                    Double.parseDouble(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Double.parseDouble(resultSet.getString(7))
            ));
        }
        return orderDetails;

    }

}
