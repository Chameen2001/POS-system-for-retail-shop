package controller;

import bo.BOFactory;
import bo.custom.impl.RemoveOrderBOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.CustomDTO;
import dto.OrderDTO;
import view.tableModel.OrderDetailTM;
import view.tableModel.OrderTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RemoveOrderFormController {

    public TextField txtCustId;
    public TableView<OrderTM> tblOrder;
    public TableColumn clmCustId;
    public TableColumn clmOrderId;
    public TableColumn clmOrderDate;
    public TableColumn clmOrderTime;
    public TableColumn clmCost;
    public TableColumn clmDelete;
    public TableView<OrderDetailTM> tblItem;
    public TableColumn clmItemCode;
    public TableColumn clmDescription;
    public TableColumn clmQuantity;
    public JFXButton btnDeleteAll;

    ObservableList<OrderTM> orderTableObservableList;
    ObservableList<OrderDetailTM> orderDetailTableObservableList;
    ArrayList<OrderDTO> orders;

    private final RemoveOrderBOImpl removeOrderBO = (RemoveOrderBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.REMOVE_ORDER_BO);

    public void initialize() {


        loadTable();

        if (orders.size() == 0)
            btnDeleteAll.setDisable(true);

        txtCustId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                orderTableObservableList.clear();
            } else {
                try {
                    loadMatcherOrders(newValue);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadOrderDetailTable(newValue.getOrderId());
            } catch (Exception e) {

            }
        });

    }

    private void loadTable() {

        try {
            orders = removeOrderBO.getAllOrders();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        loadOrderTable();

    }

    private void loadOrderDetailTable(String orderId) throws SQLException, ClassNotFoundException {

        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        orderDetailTableObservableList = FXCollections.observableArrayList();

        for (CustomDTO customDTO : removeOrderBO.getOrderDetail(orderId)) {
            orderDetailTableObservableList.add(new OrderDetailTM(customDTO.getItemCode(), customDTO.getDescription(), customDTO.getQuantity()));
        }
        tblItem.setItems(orderDetailTableObservableList);
    }

    private void loadMatcherOrders(String custId) throws SQLException, ClassNotFoundException {

        orders = removeOrderBO.getOrdersByCusIdOrOrderId(custId);

        loadOrderTable();


    }

    private void loadOrderTable() {
        clmCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        clmOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        clmOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        clmOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        clmDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        orderTableObservableList = FXCollections.observableArrayList();

        for (OrderDTO temp : orders) {
            Button delete = new Button("Delete");
            orderTableObservableList.add(new OrderTM(
                    temp.getCustomerId(),
                    temp.getOrderId(),
                    temp.getOrderDate(),
                    temp.getCost(),
                    temp.getOrderTime(),
                    delete
            ));

            delete.setOnAction(event -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to Delete this??", no, yes);
                alert.setTitle("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(no) == yes) {

                    try {
                        if (removeOrderBO.removeOrder(temp)) {
                            orders.remove(temp);
                            if (orderDetailTableObservableList != null) {
                                orderDetailTableObservableList.clear();
                            }
                            tblItem.refresh();
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Removed Order", ButtonType.OK).show();
                            loadOrderTable();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Remove Order Error", ButtonType.OK).show();
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                }

            });

        }

        tblOrder.setItems(orderTableObservableList);

    }

    public void deleteAllOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to DELETE ALL orders?\nThis can not be UNDO", yes, no);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.orElse(no) == yes) {
            try {
                if (removeOrderBO.removeAllOrders()) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted", ButtonType.OK).show();
                    loadTable();
                    btnDeleteAll.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong\nTry Again", ButtonType.OK).show();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("canceled");
        }
    }
}
