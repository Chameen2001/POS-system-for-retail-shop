package bo.custom.impl;

import bo.custom.AddNewCustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import entity.Customer;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddNewCustomerBOImpl implements AddNewCustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOImpl(DAOFactory.DAOType.CUSTOMERDAO);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();
        for (Customer customer : customerDAO.getAll()) {
            customerDTOS.add(new CustomerDTO(customer.getCusId(),customer.getCusTitle(),customer.getCusName(),customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostCode()));
        }
        return customerDTOS;
    }

    @Override
    public String getNewCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewCustomerId();
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(customerDTO.getCustomerId(),customerDTO.getCustomerTitle(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCity(),customerDTO.getProvince(),customerDTO.getPostalCode()));
    }

    @Override
    public void deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        customerDAO.delete(customerId);
    }
}
