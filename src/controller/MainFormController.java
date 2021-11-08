package controller;

import bo.BOFactory;
import bo.custom.impl.MainBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.jasperModel.ItemDetailJM;
import view.tableModel.AddToCartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainFormController {
    public BorderPane mainBoarderPane;
    public JFXComboBox<String> cmbCustomer;
    public JFXComboBox<String> cmbItem;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtItemName;
    public TextField txtDiscount;
    public TextField txtQtyOnHand;
    public TextField txtMax;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TextField txtEveryItem;


    public Pane homePane;
    public Pane addNewCustomerPane;
    public Pane modifyOrderPane;
    public Pane removeOrderPane;
    public Pane managementLogInPane;

    public Label lblOrderId;
    public TableView<AddToCartTM> tblBill;
    public TableColumn clmItemId;
    public TableColumn clmDescription;
    public TableColumn clmQuantity;
    public TableColumn clmDiscount;
    public TableColumn clmPrice;
    public Label lblSubTotal;
    public Label lblTotalDiscount;
    public Label lblTotal;
    public Label lblTime;
    public Label lblDate;
    public VBox vbxRight;
    public VBox vbxCenter;
    public JFXButton payNowBtn;
    public Button btnClear;
    public JFXButton btnAddToBill;

    int selectedIndex=-1;

    ObservableList<AddToCartTM> observableList = FXCollections.observableArrayList();

    MainBOImpl mainBO = (MainBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.MAIN_BO);

    public void initialize(){

        btnAddToBill.setDisable(true);
        txtQuantity.setEditable(false);
        if(observableList.size()==0){
            payNowBtn.setDisable(true);
        }
        btnClear.setDisable(true);
        setMenuItemColor(1);
        setComboBox();
        loadDateAdnTime();
        setOrderId();

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            try {
                setCustomer(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItem(newValue);
            txtQuantity.setEditable(true);
        });

        tblBill.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex= (int) newValue;
            btnClear.setDisable(false);
        });

        txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtQuantity.getText().equals("")){
                btnAddToBill.setDisable(true);
            }else {
                btnAddToBill.setDisable(false);
            }
        });

    }

    private void setOrderId() {

        try {
            lblOrderId.setText(mainBO.generateOrderId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItem(String id) {
        try {
            if (id==null) {
                return;
            }
            ItemDTO itemDTO=mainBO.getItem(id);
            txtItemName.setText(itemDTO.getDescription());
            txtQtyOnHand.setText(isExists(id)!=-1?String.valueOf(observableList.get(isExists(id)).getQtyOnHand()):String.valueOf(itemDTO.getQtyOnHand()));
            txtPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
            txtDiscount.setText(String.valueOf(itemDTO.getDiscount()));
            txtMax.setText(String.valueOf(itemDTO.getMaxDiscount()));
            txtEveryItem.setText(String.valueOf(itemDTO.getEveryItem()));
        } catch (SQLException | ClassNotFoundException | NullPointerException throwables ) {
            throwables.printStackTrace();
        }

        
    }

    private void setCustomer(String id) throws SQLException, ClassNotFoundException {

        if (id==null) {
            return;
        }

        CustomerDTO customerDTO = mainBO.getCustomer(id);
        try {
            txtTitle.setText(customerDTO.getCustomerTitle());
            txtName.setText(customerDTO.getCustomerName());
            txtAddress.setText(customerDTO.getCustomerAddress());
        }catch (NullPointerException exception){
            System.out.println(exception);
        }

    }

    private void setComboBox() {
        cmbCustomer.getItems().clear();
        cmbItem.getItems().clear();
        try {
            cmbCustomer.getItems().addAll(mainBO.getAllCustomersId());
            cmbItem.getItems().addAll(mainBO.getAllItemsId());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void homeOnAction(MouseEvent event) {
        cmbItem.getSelectionModel().clearSelection();
        cmbCustomer.getSelectionModel().clearSelection();
        txtQuantity.setEditable(false);
        clearAllTxtField();
        setMenuItemColor(1);
        mainBoarderPane.getChildren().remove(mainBoarderPane.getCenter());
        mainBoarderPane.setCenter(vbxCenter);
        mainBoarderPane.setRight(vbxRight);
        setComboBox();
    }

    public void addNewCustomerOnAction(MouseEvent event) throws IOException {
        setMenuItemColor(2);
        mainBoarderPane.getChildren().remove(mainBoarderPane.getCenter());
        mainBoarderPane.getChildren().remove(mainBoarderPane.getRight());
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewCustomerForm.fxml"));
        mainBoarderPane.setCenter(load);
    }

    public void managementLogInOnAction(MouseEvent event) throws IOException {
        setMenuItemColor(5);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagementLogInForm.fxml"));
        Parent load = fxmlLoader.load();
        ManagementLogInFormController managementLogInFormController=fxmlLoader.getController();
        managementLogInFormController.setPane(homePane);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Management LogIn");
        stage.show();
    }

    public void addToBillOnAction(ActionEvent event) {

        if(cmbItem.getValue()==null || txtQuantity.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Select A Item and Add Quantity",ButtonType.OK).show();
        }else {
            if(checkQtyOnHand(cmbItem.getValue())){
                int i= isExists(cmbItem.getValue());
                if(i!=-1){

                    observableList.get(i).setDiscount(getDiscount(cmbItem.getValue()));
                    observableList.get(i).setQuantity(observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText()));
                    observableList.get(i).setPrice(observableList.get(i).getPrice()+(Double.parseDouble(txtQuantity.getText())*Double.parseDouble(txtPrice.getText())));
                    tblBill.refresh();
                }else {
                    AddToCartTM addToCartTM = new AddToCartTM(
                            cmbItem.getValue(),
                            txtItemName.getText(),
                            Integer.parseInt(txtQuantity.getText()),
                            getDiscount(cmbItem.getValue()),
                            Double.parseDouble(txtPrice.getText())*Double.parseDouble(txtQuantity.getText()),
                            Integer.parseInt(txtQtyOnHand.getText()),
                            Double.parseDouble(txtDiscount.getText()),
                            Double.parseDouble(txtPrice.getText())
                    );
                    observableList.add(addToCartTM);
                    payNowBtn.setDisable(false);
                }
            }else {
                System.out.println("Not Enough Quantity");
            }



            clmItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
            clmDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            clmQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            clmDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            tblBill.setItems(observableList);
            calculateSubTotal();
        }


    }

    private boolean checkQtyOnHand(String id) {
        int quantityOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int i = isExists(id);
        if(i!=-1){
            if(Integer.parseInt(txtQuantity.getText())>Integer.parseInt(txtQtyOnHand.getText())){
                return false;
            }else {
                txtQtyOnHand.setText(String.valueOf(Integer.parseInt(txtQtyOnHand.getText())-Integer.parseInt(txtQuantity.getText())));
                observableList.get(i).setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                return true;
            }
        }else {
            if(Integer.parseInt(txtQuantity.getText())>Integer.parseInt(txtQtyOnHand.getText())){
                return false;
            }else {
                txtQtyOnHand.setText(String.valueOf(Integer.parseInt(txtQtyOnHand.getText())-Integer.parseInt(txtQuantity.getText())));
                return true;
            }
        }
    }

    private double getDiscount(String id) {
        int i = isExists(id);
        if(i!=-1){
            if((observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText()))>=Integer.parseInt(txtEveryItem.getText()) && (observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText()))<=(Double.parseDouble(txtMax.getText())/Double.parseDouble(txtDiscount.getText()))*(Integer.parseInt(txtEveryItem.getText()))){

                System.out.println(observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText()));

                double discount = (double) ((observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText()))/Integer.parseInt(txtEveryItem.getText()))*Double.parseDouble(txtDiscount.getText());
                return ((Double.parseDouble(txtPrice.getText())*(observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText())))/100d)*discount;

            }else {
                return ((Double.parseDouble(txtPrice.getText())*(observableList.get(i).getQuantity()+Integer.parseInt(txtQuantity.getText())))/100d)*Double.parseDouble(txtMax.getText());
            }
        }else {

            if(Integer.parseInt(txtQuantity.getText())>=Integer.parseInt(txtEveryItem.getText()) && Integer.parseInt(txtQuantity.getText())<=(Double.parseDouble(txtMax.getText())/Double.parseDouble(txtDiscount.getText()))*(Integer.parseInt(txtEveryItem.getText()))){
                double discount = (double) (Integer.parseInt(txtQuantity.getText())/Integer.parseInt(txtEveryItem.getText()))*Double.parseDouble(txtDiscount.getText());
                return ((Double.parseDouble(txtPrice.getText())*Integer.parseInt(txtQuantity.getText()))/100d)*discount;
            }else {
                return ((Double.parseDouble(txtPrice.getText())*Integer.parseInt(txtQuantity.getText()))/100d)*Double.parseDouble(txtMax.getText());
            }

        }
    }

    private int isExists(String id) {
        for (int i = 0 ; i<observableList.size() ; i++) {
            if(observableList.get(i).getItemId().equals(id)){
                return i;
            }
        }

        return -1;
    }

    private void setMenuItemColor(int x){

        clearAllMenuItemColor();

        switch (x){
            case 1:homePane.setStyle("-fx-background-color: #1fb9f5");break;
            case 2:addNewCustomerPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 3:modifyOrderPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 4:removeOrderPane.setStyle("-fx-background-color: #1fb9f5");break;
            case 5:managementLogInPane.setStyle("-fx-background-color: #1fb9f5");break;

        }

    }

    private void clearAllMenuItemColor() {
        homePane.setStyle("-fx-background-color: null");
        addNewCustomerPane.setStyle("-fx-background-color: null");
        modifyOrderPane.setStyle("-fx-background-color: null");
        removeOrderPane.setStyle("-fx-background-color: null");
        managementLogInPane.setStyle("-fx-background-color: null");
    }

    public void payNowOnAction(ActionEvent event) {

        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (AddToCartTM data:observableList) {
            orderDetailDTOS.add(new OrderDetailDTO(
                    lblOrderId.getText(),
                    data.getItemId(),
                    data.getQuantity(),
                    data.getDiscount(),
                    data.getUnitPrice(),
                    data.getPrice(),
                    data.getPercentage()
            ));
        }

        OrderDTO orderDTO = new OrderDTO(
                cmbCustomer.getValue(),
                lblOrderId.getText(),
                lblDate.getText(),
                Double.parseDouble(lblTotal.getText()),
                lblTime.getText(),
                orderDetailDTOS
        );

        try {
            if(mainBO.placeOrder(orderDTO)){
                new Alert(Alert.AlertType.CONFIRMATION,"Place Order Successfully",ButtonType.OK).show();
                clearTable();
                btnClear.setDisable(true);
                payNowBtn.setDisable(true);
                setOrderId();

            }else {
                new Alert(Alert.AlertType.ERROR,"Place Order Error",ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException | JRException throwables) {
            throwables.printStackTrace();
        }


    }

    public void clearOnAction(ActionEvent event) {
        observableList.remove(selectedIndex);
        calculateSubTotal();
        setItem(cmbItem.getValue());
        if(observableList.size()==0){
            payNowBtn.setDisable(true);
            btnClear.setDisable(true);
        }


    }

    private void calculateSubTotal(){
        double subTotal = 0.0;
        double discount = 0.0;

        for (AddToCartTM data : observableList) {
            subTotal+=data.getPrice();
            discount+=data.getDiscount();
        }

        lblTotal.setText(String.valueOf(subTotal-discount));

        lblSubTotal.setText(String.valueOf(subTotal));
        lblTotalDiscount.setText(String.valueOf(discount));

    }

    public void clearTable(){
        observableList.clear();
    }

    private void loadDateAdnTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(format.format(date));

        //Time--------------------------

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime localTime = LocalTime.now();
            lblTime.setText(
                    localTime.getHour()+":"+localTime.getMinute()+":"+localTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void modifyOrderOnAction(MouseEvent event) throws IOException {

        setMenuItemColor(3);
        mainBoarderPane.getChildren().remove(mainBoarderPane.getCenter());
        mainBoarderPane.getChildren().remove(mainBoarderPane.getRight());
        Parent load = FXMLLoader.load(getClass().getResource("../view/ModifyOrderForm.fxml"));
        mainBoarderPane.setCenter(load);

    }

    public void removeOrderOnAction(MouseEvent event) throws IOException {

        setMenuItemColor(4);
        mainBoarderPane.getChildren().remove(mainBoarderPane.getCenter());
        mainBoarderPane.getChildren().remove(mainBoarderPane.getRight());
        Parent load = FXMLLoader.load(getClass().getResource("../view/RemoveOrderForm.fxml"));
        mainBoarderPane.setCenter(load);

    }

    private void clearAllTxtField(){
        txtTitle.clear();
        txtQuantity.clear();
        txtQtyOnHand.clear();
        txtEveryItem.clear();
        txtMax.clear();
        txtDiscount.clear();
        txtPrice.clear();
        txtItemName.clear();
        txtAddress.clear();
        txtName.clear();
    }

}
