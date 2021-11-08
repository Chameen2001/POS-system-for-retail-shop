package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String generateNewCustomerId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCustomersId() throws SQLException, ClassNotFoundException;

}
