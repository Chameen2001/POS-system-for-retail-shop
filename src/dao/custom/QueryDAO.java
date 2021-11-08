package dao.custom;

import dao.SuperDAO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
}
