package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {

    ArrayList<OrderDetail> getItemMovableData() throws SQLException, ClassNotFoundException;

    double getPriceFromOrder(String orderId) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String orderId, String itemId) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;


}
