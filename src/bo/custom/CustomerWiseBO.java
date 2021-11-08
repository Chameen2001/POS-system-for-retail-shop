package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerWiseBO extends SuperBO {

    ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException, ClassNotFoundException;

}
