package bo.custom.impl;

import dao.DAOFactory;
import db.DbConnection;
import bo.custom.MainBO;
import dao.custom.*;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.jasperModel.ItemDetailJM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainBOImpl implements MainBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.CUSTOMERDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ITEMDAO);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDAO);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDETAILDAO);


    @Override
    public ItemDTO getItem(String id) throws SQLException, ClassNotFoundException {

        Item item = itemDAO.get(id);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice(), item.getDiscount(), item.getEveryItem(), item.getMaxDiscount());
    }

    @Override
    public CustomerDTO getCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.get(id);
        return new CustomerDTO(customer.getCusId(), customer.getCusTitle(), customer.getCusName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostCode());
    }

    @Override
    public ArrayList<String> getAllCustomersId() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomersId();
    }

    @Override
    public ArrayList<String> getAllItemsId() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemsId();
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateOrderId();
    }

    @Override
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException, JRException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        if (orderDAO.add(new Order(orderDTO.getCustomerId(), orderDTO.getOrderId(), orderDTO.getOrderDate(), orderDTO.getCost(), orderDTO.getOrderTime()))) {
            for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailDTOS()) {
                if (!orderDetailDAO.add(new OrderDetail(orderDetailDTO.getOrderId(), orderDetailDTO.getItemCode(), orderDetailDTO.getQuantity(), orderDetailDTO.getDiscount(), orderDetailDTO.getUnitPrice(), orderDetailDTO.getPrice(), orderDetailDTO.getPercentage()))) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                Item item = itemDAO.get(orderDetailDTO.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetailDTO.getQuantity());
                if (!itemDAO.update(item)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
        } else {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        connection.commit();
        connection.setAutoCommit(true);
        HashMap map = new HashMap();
        map.put("time",orderDTO.getOrderTime());
        map.put("date",orderDTO.getOrderDate());
        map.put("total",String.valueOf(orderDTO.getCost()));

        ItemDetailJM[] itemDetailJMS = new ItemDetailJM[orderDTO.getOrderDetailDTOS().size()];

        for (int i = 0; i < orderDTO.getOrderDetailDTOS().size(); i++) {
            itemDetailJMS[i] = new ItemDetailJM(itemDAO.get(orderDTO.getOrderDetailDTOS().get(i).getItemCode()).getDescription(),String.valueOf(orderDTO.getOrderDetailDTOS().get(i).getQuantity()),String.valueOf(orderDTO.getOrderDetailDTOS().get(i).getUnitPrice()),String.valueOf(orderDTO.getOrderDetailDTOS().get(i).getPrice()));
        }

        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/view/reports/CustomerBill.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(itemDetailJMS));
        JasperViewer.viewReport(jasperPrint,false);
        return true;

    }
}
