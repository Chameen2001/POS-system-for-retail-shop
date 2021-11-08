package bo.custom.impl;

import dao.DAOFactory;
import db.DbConnection;
import bo.custom.RemoveOrderBO;
import dao.custom.*;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import dao.custom.impl.QueryDAOImpl;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import dto.CustomDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveOrderBOImpl implements RemoveOrderBO {

    private final OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDAO);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.QUERYDAO);
    private final ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);
    OrderDetailDAOImpl orderDetailDAO = (OrderDetailDAOImpl) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDETAILDAO);

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderDAO.getAll()) {
            orderDTOS.add(new OrderDTO(order.getCustId(), order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime()));
        }
        return orderDTOS;
    }

    @Override
    public ArrayList<CustomDTO> getOrderDetail(String orderId) throws SQLException, ClassNotFoundException {
        return queryDAO.getOrderDetails(orderId);
    }

    @Override
    public ArrayList<OrderDTO> getOrdersByCusIdOrOrderId(String custId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderDAO.getOrdersByCusIdOrOrderId(custId)) {
            orderDTOS.add(new OrderDTO(order.getCustId(), order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime()));
        }
        return orderDTOS;
    }

    @Override
    public boolean removeOrder(OrderDTO order) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        for (CustomDTO orderDetail : queryDAO.getOrderDetails(order.getOrderId())) {
            Item item = itemDAO.get(orderDetail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand()+orderDetail.getQuantity());
            if (!itemDAO.update(item)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        
        if (!orderDAO.delete(order.getOrderId())) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean removeAllOrders() throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        for (OrderDetail orderDetail : orderDetailDAO.getAll()) {
            Item item = itemDAO.get(orderDetail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand()+orderDetail.getQuantity());
            if (!itemDAO.update(item)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        if (!orderDAO.deleteAllOrders()) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        connection.commit();
        connection.setAutoCommit(false);
        return true;

    }
}
