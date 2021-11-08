package dao.custom;

import dao.CrudDAO;
import dao.CrudUtil;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public interface OrderDAO extends CrudDAO<Order,String> {

    ArrayList<Order> getOrdersLike(String id) throws SQLException, ClassNotFoundException;

    ArrayList<Order> getOrdersByCusIdOrOrderId(String id) throws SQLException, ClassNotFoundException;

    String generateOrderId() throws SQLException, ClassNotFoundException;

    public boolean deleteAllOrders() throws SQLException, ClassNotFoundException;

    public ArrayList<Order> getTodayOrderTimesAndCosts() throws SQLException, ClassNotFoundException;

    public ArrayList<Order> getMonthlyOrderDateAndCost() throws SQLException, ClassNotFoundException;

    public ArrayList<Order> getAnnuallyOrderDateAndCost() throws SQLException, ClassNotFoundException;
}
