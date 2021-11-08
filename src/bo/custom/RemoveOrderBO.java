package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RemoveOrderBO extends SuperBO {

    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getOrderDetail(String orderId) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getOrdersByCusIdOrOrderId(String custId) throws SQLException, ClassNotFoundException;

    boolean removeOrder(OrderDTO order) throws SQLException, ClassNotFoundException;

    boolean removeAllOrders() throws SQLException, ClassNotFoundException;
}
