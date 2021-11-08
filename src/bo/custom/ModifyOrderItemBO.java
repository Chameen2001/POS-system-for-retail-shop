package bo.custom;

import bo.SuperBO;
import dto.OrderDetailDTO;


import java.sql.SQLException;

public interface ModifyOrderItemBO extends SuperBO {

    boolean saveChanges(OrderDetailDTO orderDetailDTO, int changedQuantity) throws SQLException, ClassNotFoundException;

}

