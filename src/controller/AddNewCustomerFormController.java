package controller;

import bo.BOFactory;
import bo.custom.AddNewCustomerBO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import dto.CustomerDTO;
import util.ValidationUtil;
import view.tableModel.CustomerTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddNewCustomerFormController {
    public AnchorPane addNewCustomerPane;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn clmCustId;
    public TableColumn clmCustName;
    public TableColumn clmAddress;
    public TableColumn clmPostalCode;
    public TableColumn clmCity;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public ComboBox<String> cmbTitle;

    ObservableList<CustomerTM> observableList;

    private final AddNewCustomerBO addNewCustomerBO = (AddNewCustomerBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.ADD_NEW_CUSTOMER_BO);

    int selectedIndex;


    Pattern namePattern = Pattern.compile("^[A-Z][A-z]*( [A-Z][A-z]*)?$");
    Pattern provincePattern = Pattern.compile("^[A-Z][A-z]*( [A-Z][A-z]*)?$");
    Pattern addressPattern = Pattern.compile("[A-z0-9]*\\s*,*.*");
    Pattern cityPattern = Pattern.compile("^[A-z0-9]*\\s*[A-z0-9]*$");
    Pattern postalCodePattern = Pattern.compile("^[0-9]{3,}$");

    LinkedHashMap<TextField,Pattern> regExMap = new LinkedHashMap<>();
    

    public void initialize(){

        initializeRegExMap();
        initializeCmbTitle();
        btnSave.setDisable(true);
        setCustomerId();
        loadTable();
        btnDelete.setDisable(true);
        tblCustomer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex= (int) newValue;
            btnDelete.setDisable(false);
        });

    }

    private void initializeCmbTitle() {
        cmbTitle.getItems().addAll("Mr","Miss","Mis");
    }

    private void initializeRegExMap() {
        regExMap.put(txtCustomerName,namePattern);
        regExMap.put(txtProvince,provincePattern);
        regExMap.put(txtAddress,addressPattern);
        regExMap.put(txtPostalCode,postalCodePattern);
        regExMap.put(txtCity,cityPattern);
    }

    private void loadTable() {
        clmCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        clmPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        clmCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        try {
            observableList= FXCollections.observableArrayList();
            for (CustomerDTO customer : addNewCustomerBO.getAllCustomers()) {
                observableList.add(new CustomerTM(customer.getCustomerId(),customer.getCustomerTitle(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblCustomer.setItems(observableList);
    }

    private void setCustomerId() {
        try {
            txtCustomerId.setText(addNewCustomerBO.getNewCustomerId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveOnAction(ActionEvent event) {
        CustomerDTO customerDTO = new CustomerDTO(
                txtCustomerId.getText(),
                cmbTitle.getSelectionModel().getSelectedItem(),
                txtCustomerName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        try {
            if(addNewCustomerBO.saveCustomer(customerDTO)){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added Successfully",ButtonType.OK).show();
                setCustomerId();
                loadTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Cannot Add Customer\nTry Again",ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE,"Do you want to delete selected customer?",yes,no);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.orElse(no)==yes){
            try {
                addNewCustomerBO.deleteCustomer(observableList.get(selectedIndex).getCustomerId());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            loadTable();
            setCustomerId();
            btnDelete.setDisable(true);
        }
    }


    public void validationOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(regExMap,btnSave);

        if (keyEvent.getCode()== KeyCode.ENTER) {
            ValidationUtil.requestFocus(regExMap,btnSave);
        }
    }
}
