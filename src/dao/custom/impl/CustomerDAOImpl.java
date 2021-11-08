package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public String generateNewCustomerId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT custId FROM Customer ORDER BY custId DESC LIMIT 1");
        if (resultSet.next()) {
            int id = Integer.parseInt(resultSet.getString(1).split("-")[1]);
            id = ++id;
            if (id < 9) {
                return "C-00" + id;
            } else if (id < 99) {
                return "C-0" + id;
            } else {
                return "C-" + id;
            }
        }
        return "C-001";
    }

    @Override
    public ArrayList<String> getAllCustomersId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT custId FROM Customer");
        ArrayList<String> customerIds = new ArrayList<>();
        while (resultSet.next()) {
            customerIds.add(resultSet.getString(1));
        }
        return customerIds;
    }

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)", customer.getCusId(), customer.getCusTitle(), customer.getCusName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostCode());


    }

    @Override
    public boolean update(Customer object) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return customers;

    }

    @Override
    public Customer get(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = (ResultSet) CrudUtil.execute("SELECT * FROM Customer WHERE custId=?",id);
        Customer customer = null;
        if (resultSet.next()) {
            customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return customer;

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return (boolean) CrudUtil.execute("DELETE FROM Customer WHERE custId=?",id);

    }

}
