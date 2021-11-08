package bo.custom.impl;

import dao.DAOFactory;
import db.DbConnection;
import bo.custom.ModifyOrderBO;
import dao.custom.*;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import view.tableModel.OrderDetailTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyOrderBOImpl implements ModifyOrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDAO);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDETAILDAO);
    private final ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {


        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderDAO.getAll()) {
            orderDTOS.add(new OrderDTO(order.getCustId(), order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime()));
        }
        return orderDTOS;
    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailDAO.getOrderDetails(orderId)) {
            orderDetailDTOS.add(new OrderDetailDTO(orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getUnitPrice(), orderDetail.getPrice(), orderDetail.getPercentage()));
        }
        return orderDetailDTOS;
    }

    @Override
    public ArrayList<OrderDTO> getOrdersLike(String id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderDAO.getOrdersLike(id)) {
            orderDTOS.add(new OrderDTO(order.getCustId(), order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime()));
        }
        return orderDTOS;
    }

    @Override
    public boolean deleteItem(String orderId, String itemId) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.deleteItem(orderId, itemId);
    }

    @Override
    public ItemDTO getItem(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.get(itemCode);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount());
    }

    @Override
    public boolean updateQuantity(Item item) throws SQLException, ClassNotFoundException {
        return itemDAO.update(item);
    }

    @Override
    public boolean deleteItemFromOrder(OrderDetailTM orderDetailTM, String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        if (orderDetailDAO.deleteItem(orderId, orderDetailTM.getItemCode())) {

            Item item = itemDAO.get(orderDetailTM.getItemCode());

            Item item1 = new Item();
            item1.setQtyOnHand(orderDetailTM.getQuantity() + item.getQtyOnHand());

            if (itemDAO.update(item)) {
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }
        }
        connection.rollback();
        connection.setAutoCommit(true);
        return false;
    }

}
