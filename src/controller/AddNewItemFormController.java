package controller;

import bo.BOFactory;
import bo.custom.AddNewItemBO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewItemFormController {

    public TextField txtDescription;
    public TextField txtItemCode;
    public TextField txtQtyOnHand;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtDiscount;
    public TextField txtEveryItem;
    public TextField txtMaxDiscount;

    public TableColumn clmCode;
    public TableColumn clmDescription;
    public TableColumn clmQtyOnHand;
    public TableColumn clmPackSize;
    public TableColumn clmUnitPrize;
    public TableColumn clmDiscount;
    public TableColumn clmEveryItem;
    public TableColumn clmMaxDiscount;
    public JFXButton btnSave;

    public TableView<ItemDTO> tblItems;

    private final AddNewItemBO addNewItemBO = (AddNewItemBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.ADD_NEW_ITEM_BO);

    Pattern itemCodePattern = Pattern.compile("^I-[0-9]{3,}$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern packSizePattern = Pattern.compile("^([1-4][0-9]{0,2}|5[0-9]|500)$");
    Pattern unitPricePattern = Pattern.compile("^[1-9]?[0-9]*\\.[0-9]$");
    Pattern descriptionPattern = Pattern.compile("^[A-z0-9]*\\s?[A-z0-9]*$");
    Pattern discountPattern = Pattern.compile("^[1-9]?[0-9]\\.[0-9]$");
    Pattern everyItemPattern = Pattern.compile("^[1-9][0-9]{0,2}$");
    Pattern maxDiscountPattern = Pattern.compile("^[1-9]?[0-9]\\.[0-9]$");

    LinkedHashMap<TextField,Pattern> regExMap = new LinkedHashMap<>();



    public void initialize(){
        loadTableData();
        initializeLinkedHashMap();
    }

    private void initializeLinkedHashMap() {
        regExMap.put(txtItemCode,itemCodePattern);
        regExMap.put(txtQtyOnHand,qtyPattern);
        regExMap.put(txtPackSize, packSizePattern);
        regExMap.put(txtUnitPrice,unitPricePattern);
        regExMap.put(txtDescription,descriptionPattern);
        regExMap.put(txtDiscount,discountPattern);
        regExMap.put(txtEveryItem,everyItemPattern);
        regExMap.put(txtMaxDiscount,maxDiscountPattern);
    }

    private void loadTableData() {
        clmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        clmUnitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        clmEveryItem.setCellValueFactory(new PropertyValueFactory<>("everyItem"));
        clmMaxDiscount.setCellValueFactory(new PropertyValueFactory<>("maxDiscount"));

        ObservableList<ItemDTO> observableList;

        ArrayList<ItemDTO> itemDTOS = null;
        try {
            itemDTOS = addNewItemBO.getAllItems();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if(itemDTOS !=null){
             observableList= FXCollections.observableArrayList(itemDTOS);
            tblItems.setItems(observableList);
        }
    }

    public void saveOnAction(ActionEvent event) {
        ItemDTO itemDTO = new ItemDTO(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Integer.parseInt(txtQtyOnHand.getText()),
                Double.parseDouble(txtUnitPrice.getText()),
                Double.parseDouble(txtDiscount.getText()),
                Integer.parseInt(txtEveryItem.getText()),
                Double.parseDouble(txtMaxDiscount.getText())
        );

        try {
            if(addNewItemBO.addItem(itemDTO)){
                System.out.println("Successfully Added an Item");
                loadTableData();
            }else {
                System.out.println("Try Again");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void validateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(regExMap,btnSave);
        if (keyEvent.getCode()== KeyCode.ENTER){
            ValidationUtil.requestFocus(regExMap,btnSave);
        }
    }
}
