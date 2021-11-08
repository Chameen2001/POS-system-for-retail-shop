package bo.custom.impl;

import dao.DAOFactory;
import db.DbConnection;
import bo.custom.ModifyOrderItemBO;
import dao.custom.*;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ModifyOrderItemBOImpl implements ModifyOrderItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDETAILDAO);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDAO);

    @Override
    public boolean saveChanges(OrderDetailDTO orderDetailDTO, int changedQuantity) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        Item item = itemDAO.get(orderDetailDTO.getItemCode());

        if (enoughQuantity(changedQuantity, item.getQtyOnHand())) {

            double discount = getDiscount(orderDetailDTO.getQuantity() + changedQuantity, item);
            System.out.println("Discount : "+discount);

            double payablePrice = (item.getUnitPrice() * (orderDetailDTO.getQuantity() + changedQuantity)) - discount;
            System.out.println("Payable price : "+payablePrice);

            connection.setAutoCommit(false);
            if (orderDetailDAO.update(new OrderDetail(orderDetailDTO.getOrderId(), orderDetailDTO.getItemCode(), orderDetailDTO.getQuantity() + changedQuantity, discount, payablePrice))) {
                item.setQtyOnHand(item.getQtyOnHand() + (changedQuantity >= 0 ? -changedQuantity : -changedQuantity));

                if (itemDAO.update(item)) {
                    double priceFromOrder = orderDetailDAO.getPriceFromOrder(orderDetailDTO.getOrderId());

                    Order order = new Order();
                    order.setOrderId(orderDetailDTO.getOrderId());
                    order.setCost(priceFromOrder);

                    if (orderDAO.update(order)) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        return true;
                    }
                } else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    System.out.println("Item Update Error!");
                    return false;
                }
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("Order Detail Update Error!");
                return false;
            }
        } else {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Quantity Not Enough");
            return false;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        return false;
    }

    private static boolean enoughQuantity(int quantity, int qtyOnHand) {
        if (quantity > qtyOnHand) {
            System.out.println("Not Enough Quantity");
            return false;
        } else {
            System.out.println("Enough Quantity");
            return true;
        }

    }

    private static double getDiscount(int quantity, Item item) {
        System.out.println("Quantity In getDiscount : "+quantity);
        if (quantity >= item.getEveryItem() && quantity <= (item.getMaxDiscount() / item.getDiscount()) * item.getEveryItem()) {
            System.out.println("Eligible For Discount");
            double newDiscount = (double) (quantity / item.getEveryItem()) * item.getDiscount();
            return (item.getUnitPrice() * quantity / 100d) * newDiscount;
        } else if (quantity < item.getEveryItem()) {
            return 0.0;
        } else {
            return (item.getUnitPrice() * quantity / 100d) * item.getMaxDiscount();
        }

    }

}
