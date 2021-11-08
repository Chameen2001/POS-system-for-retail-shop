package bo.custom;

import bo.SuperBO;
import entity.Item;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import view.tableModel.OrderDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ModifyOrderBO extends SuperBO {

    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getOrdersLike(String id) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String orderId, String itemId) throws SQLException, ClassNotFoundException;

    ItemDTO getItem(String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateQuantity(Item item) throws SQLException, ClassNotFoundException;

    boolean deleteItemFromOrder(OrderDetailTM orderDetailTM, String orderId) throws SQLException, ClassNotFoundException;
}
