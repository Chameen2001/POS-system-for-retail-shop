package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getCustomerWiseIncome() throws SQLException, ClassNotFoundException {

        ArrayList<CustomDTO> customerWiseIncomeData = new ArrayList<>();
        ResultSet resultSet = (ResultSet) CrudUtil.execute("select c.custId,c.custTitle,c.custName,c.custAddress,SUM(o.cost) FROM Customer c INNER JOIN `Order` o ON o.custId=c.custId GROUP BY c.custId");
        while (resultSet.next()) {
            customerWiseIncomeData.add(new CustomDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))

            ));
        }
        return customerWiseIncomeData;

    }

    public ArrayList<CustomDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> customDTOS = new ArrayList<>();

            ResultSet resultSet = (ResultSet) CrudUtil.execute("select o.itemCode,i.description,o.quantity from `Order Detail` o INNER JOIN Item i ON o.itemCode=i.itemCode WHERE o.orderId=?",orderId);
            while (resultSet.next()){
                customDTOS.add(new CustomDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        Integer.parseInt(resultSet.getString(3))
                ));
            }
            return customDTOS;

    }

}
