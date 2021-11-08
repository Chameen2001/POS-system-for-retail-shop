package bo.custom.impl;

import bo.custom.ManagementBO;
import dao.DAOFactory;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDetailDAOImpl;
import entity.OrderDetail;
import dto.ItemMovableData;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagementBOImpl implements ManagementBO {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.ORDERDETAILDAO);

    public ArrayList<ItemMovableData> getItemMovableData() throws SQLException, ClassNotFoundException {
        ArrayList<ItemMovableData> itemMovableDataS = new ArrayList<>();
        for (OrderDetail itemMovableData : orderDetailDAO.getItemMovableData()) {
            itemMovableDataS.add(new ItemMovableData(itemMovableData.getItemCode(),itemMovableData.getQuantity()));
        }
        return itemMovableDataS;
    }
}
