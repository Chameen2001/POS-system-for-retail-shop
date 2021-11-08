package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return (boolean) CrudUtil.execute("INSERT INTO `Order` VALUES (?,?,?,?,?)", order.getCustId(), order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime());
    }

    @Override
    public boolean update(Order object) throws SQLException, ClassNotFoundException {
        return (boolean) CrudUtil.execute("UPDATE `Order` SET cost=? WHERE orderId=?", object.getCost(), object.getOrderId());
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM `Order`");
        ArrayList<entity.Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            orders.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4)),
                    resultSet.getString(5)

            ));
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getOrdersLike(String id) throws SQLException, ClassNotFoundException {

        ArrayList<Order> orderData = new ArrayList<>();
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM `Order` WHERE custId LIKE ?", id + "%");
        while (resultSet.next()) {
            orderData.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4)),
                    resultSet.getString(5)
            ));
        }
        return orderData;

    }

    @Override
    public ArrayList<Order> getOrdersByCusIdOrOrderId(String id) throws SQLException, ClassNotFoundException {

        ArrayList<Order> orders = new ArrayList<>();

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM `Order` WHERE custId LIKE ? OR orderId LIKE ?", id + "%", id + "%");
        while (resultSet.next()) {
            orders.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4)),
                    resultSet.getString(5)
            ));
        }
        return orders;

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("DELETE FROM `Order` WHERE orderId=?", id);

    }

    @Override
    public Order get(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAllOrders() throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("DELETE FROM `Order`");

    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if (resultSet.next()) {
            int nextNumber = Integer.parseInt(resultSet.getString(1).replace("O-", "")) + 1;
            return String.format("O-%03d", nextNumber);
        } else {
            return "O-001";
        }
    }

    @Override
    public ArrayList<Order> getTodayOrderTimesAndCosts() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT orderTime,cost FROM `Order` WHERE orderDate=?", format.format(date));
        while (resultSet.next()) {
            orders.add(new Order(
                    Double.parseDouble(resultSet.getString(2)),
                    resultSet.getString(1)
            ));
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getMonthlyOrderDateAndCost() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT orderDate,cost FROM `Order` WHERE orderDate LIKE ?", format.format(date) + "%");
        while (resultSet.next()) {
            orders.add(new Order(
                    resultSet.getString(1),
                    Double.parseDouble(resultSet.getString(2))
            ));
        }
        return orders;
    }

    @Override
    public ArrayList<Order> getAnnuallyOrderDateAndCost() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT orderDate,cost FROM `Order` WHERE orderDate LIKE ?",format.format(date)+"%");
        while (resultSet.next()) {
            orders.add(new Order(
                    resultSet.getString(1),
                    Double.parseDouble(resultSet.getString(2))
            ));
        }
        return orders;
    }

}
