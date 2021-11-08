package controller;

import bo.BOFactory;
import bo.custom.impl.ManagementBOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import dto.ItemMovableData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagementFormController {
    public AnchorPane anchorPane;
    public Pane reportPane;
    public Pane addNewItemPane;
    public Pane modifyPane;
    public Pane removePane;
    public Pane backToCashierPane;
    public AnchorPane reportContext;
    public PieChart movablePieChart;

    ManagementBOImpl managementBO = (ManagementBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.MANAGEMENT_BO);

    public void initialize(){

        setMenuItemColor(1);
        try {
            initializePieChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializePieChart() throws SQLException, ClassNotFoundException {

        ArrayList<ItemMovableData> data = managementBO.getItemMovableData();

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        for (ItemMovableData data1 :data) {
            list.add(new PieChart.Data(
                    data1.getItemCode(),
                    data1.getQuantity()
            ));
        }

        movablePieChart.setData(list);

    }

    public void addNewItemOnAction(MouseEvent event) throws IOException {
        setMenuItemColor(2);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/AddNewItemForm.fxml")));
    }

    public void modifyItemOnAction(MouseEvent event) throws IOException {
        setMenuItemColor(3);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ModifyItemForm.fxml")));
    }

    public void removeItemOnAction(MouseEvent event) throws IOException {
        setMenuItemColor(4);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/RemoveItemForm.fxml")));
    }

    public void reportOnAction(MouseEvent event) {
        setMenuItemColor(1);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(reportContext);
    }

    public void backToCashierForm(MouseEvent event) throws IOException {
        setMenuItemColor(5);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"))));
        stage.setTitle("JDBC Store");
        stage.show();
    }
    private void setMenuItemColor(int x){

        clearAllMenuItemColor();

        switch (x){
            case 1:reportPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 2:addNewItemPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 3:modifyPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 4:removePane.setStyle("-fx-background-color: #1fb9f5");break;
            case 5:backToCashierPane.setStyle("-fx-background-color: #1fb9f5");break;

        }

    }

    private void clearAllMenuItemColor() {
        reportPane.setStyle("-fx-background-color: null");
        addNewItemPane.setStyle("-fx-background-color: null");
        modifyPane.setStyle("-fx-background-color: null");
        removePane.setStyle("-fx-background-color: null");
        backToCashierPane.setStyle("-fx-background-color: null");
    }

    public void incomeReportOnAction(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/IncomeReportForm.fxml"));
        Parent load = fxmlLoader.load();
        IncomeReportFormController incomeReportFormController = fxmlLoader.getController();
        incomeReportFormController.setPane(anchorPane,reportContext);
        anchorPane.getChildren().add(load);
    }

    public void customerWiseOnAction(ActionEvent event) throws IOException {
        anchorPane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CustomerWiseIncomeForm.fxml"));
        Parent load = fxmlLoader.load();
        CustomerWiseIncomeFormController customerWiseIncomeFormController = fxmlLoader.getController();
        customerWiseIncomeFormController.setPane(anchorPane,reportContext);
        anchorPane.getChildren().add(load);
    }
}
