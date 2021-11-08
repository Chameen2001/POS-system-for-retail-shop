package bo.custom.impl;

import bo.custom.CustomerWiseBO;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import dao.custom.impl.QueryDAOImpl;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerWiseBOImpl implements CustomerWiseBO {

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.QUERYDAO);

    public ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException, ClassNotFoundException {
        return queryDAO.getCustomerWiseIncome();
    }
}
