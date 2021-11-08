package controller;

import bo.BOFactory;
import bo.custom.ModifyOrderBO;
import bo.custom.impl.ModifyOrderBOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import view.tableModel.OrderDetailTM;
import view.tableModel.OrderTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ModifyOrderFormController {

    public TableView<OrderTM> tblOrder;
    public TableColumn clmOrderId;
    public TableColumn clmOrderDate;
    public TableColumn clmOrderTime;
    public TableColumn clmCost;
    public TableView<OrderDetailTM> tblOrderDetail;
    public TableColumn clmItemCode;
    public TableColumn clmQuantity;
    public TableColumn clmUnitPrice;
    public TableColumn clmPercentage;
    public TableColumn clmDiscount;
    public TableColumn clmSellPrice;
    public TextField txtSearchCustomer;
    public Button btnDelete;
    public Button btnEdit;


    ObservableList<OrderTM> orderTableObservableList;
    ObservableList<OrderDetailTM> orderDetailsTableObservableList;

    int selectedItemIndex = -1;
    String lastSelectedOrderID;

    private final ModifyOrderBO modifyOrderBO = (ModifyOrderBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.MODIFY_ORDER_BO);

    public void initialize() {
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        loadTable();

        txtSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                loadTable();
            } else {
                try {
                    loadOrderTable(newValue);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadOrderDetailTable(newValue.getOrderId());
                lastSelectedOrderID = newValue.getOrderId();
            } catch (NullPointerException | SQLException | ClassNotFoundException ignored) {

            }
        });

        tblOrderDetail.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
            try {
                selectedItemIndex = (int) newValue;
            } catch (Exception ignored) {

            }
        });

    }

    private void loadTable() {
        clmOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        clmOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmOrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("cost"));


        try {
            ObservableList<OrderTM> observableList = FXCollections.observableArrayList();

            for (OrderDTO order : modifyOrderBO.getAllOrders()) {
                observableList.add(new OrderTM(order.getOrderId(), order.getOrderDate(), order.getCost(), order.getOrderTime()));
            }

            tblOrder.setItems(observableList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    private void loadOrderDetailTable(String orderId) throws SQLException, ClassNotFoundException {
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        clmDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        clmSellPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderDetailsTableObservableList = FXCollections.observableArrayList();

        for (OrderDetailDTO orderDetail : modifyOrderBO.getOrderDetails(orderId)) {
            orderDetailsTableObservableList.add(new OrderDetailTM(orderDetail.getItemCode(), orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getUnitPrice(), orderDetail.getPrice(), orderDetail.getPercentage()));
        }

        tblOrderDetail.setItems(orderDetailsTableObservableList);
    }

    private void loadOrderTable(String id) throws SQLException, ClassNotFoundException {
        clmOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        clmOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmOrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("cost"));


        orderTableObservableList = FXCollections.observableArrayList();
        for (OrderDTO orderDTO : modifyOrderBO.getOrdersLike(id)) {
            orderTableObservableList.add(new OrderTM(orderDTO.getOrderId(), orderDTO.getOrderDate(), orderDTO.getCost(), orderDTO.getOrderTime()));
        }
        try {
            tblOrder.setItems(orderTableObservableList);
        } catch (NullPointerException exception) {

        }
    }

    public void editOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ModifyItemDetailsForm.fxml"));
        Parent load = fxmlLoader.load();
        ModifyOrderItemFormController modifyOrderItemFormController = fxmlLoader.getController();
        modifyOrderItemFormController.setData(orderDetailsTableObservableList.get(selectedItemIndex), lastSelectedOrderID, btnEdit);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();

    }

    public void deleteOnAction(ActionEvent event) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this??", no, yes);
        alert.setTitle("Delete");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(no) == yes) {
            try {
                OrderDetailTM orderDetailTM = orderDetailsTableObservableList.get(selectedItemIndex);
                if (modifyOrderBO.deleteItemFromOrder(orderDetailTM,lastSelectedOrderID)) {
                    orderDetailsTableObservableList.remove(selectedItemIndex);
                    btnDelete.setDisable(true);
                    btnEdit.setDisable(true);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
