package controller;

import bo.BOFactory;
import bo.custom.impl.CustomerWiseBOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerWiseIncomeFormController {


    public TableView tblCustomer;
    public TableColumn clmCustId;
    public TableColumn clmTitle;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmIncome;
    public PieChart pieChart;
    private AnchorPane anchorPane;
    private AnchorPane reportContext;

    CustomerWiseBOImpl customerWiseBO = (CustomerWiseBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.CUSTOMER_WISE_BO);

    public void initialize(){

        try {
            loadTable();
            loadPieChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadPieChart() throws SQLException, ClassNotFoundException {

        ArrayList<CustomDTO> customerWiseIncome = customerWiseBO.getCustomerWiseIncome();

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        for (CustomDTO data1 :customerWiseIncome) {
            list.add(new PieChart.Data(
                    data1.getName(),
                    data1.getCost()
            ));
        }

        pieChart.setData(list);

    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        clmCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmIncome.setCellValueFactory(new PropertyValueFactory<>("cost"));

        ObservableList<CustomDTO> observableList = FXCollections.observableArrayList(customerWiseBO.getCustomerWiseIncome());
        tblCustomer.setItems(observableList);
    }

    public void backOnAction(ActionEvent event) {
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(reportContext);
    }

    public void setPane(AnchorPane anchorPane, AnchorPane reportContext) {
        this.anchorPane=anchorPane;
        this.reportContext=reportContext;
    }
}
