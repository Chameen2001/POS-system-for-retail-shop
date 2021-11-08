package controller;

import bo.BOFactory;
import bo.custom.impl.EditItemDetailBOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import dto.ItemDTO;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EditItemDetailFormController {
    public TextField txtItemCode;
    public TextField txtQtyOnHand;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtDiscount;
    public TextField txtEveryItem;
    public TextField txtMaxDiscount;
    public TextField txtDescription;
    public JFXButton btnUpdate;

    String actualId;
    ModifyItemFormController controller;

    private final EditItemDetailBOImpl editItemDetailBO = (EditItemDetailBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.EDIT_ITEM_DETAIL_BO);

    Pattern itemCodePattern = Pattern.compile("^I-[0-9]{3,}$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern packSizePattern = Pattern.compile("^([1-4][0-9]{0,2}|5[0-9]|500)$");
    Pattern unitPricePattern = Pattern.compile("^[1-9]?[0-9]*\\.[0-9]$");
    Pattern descriptionPattern = Pattern.compile("^[A-z0-9]*\\s?[A-z0-9]*$");
    Pattern discountPattern = Pattern.compile("^[1-9]?[0-9]\\.[0-9]$");
    Pattern everyItemPattern = Pattern.compile("^[1-9][0-9]{0,2}$");
    Pattern maxDiscountPattern = Pattern.compile("^[1-9]?[0-9]\\.[0-9]$");

    LinkedHashMap<TextField, Pattern> regExMap = new LinkedHashMap<>();

    public void initialize() {
        initializeRegExMap();
        ValidationUtil.validate(regExMap,btnUpdate);
    }

    private void initializeRegExMap() {

        regExMap.put(txtItemCode,itemCodePattern);
        regExMap.put(txtQtyOnHand,qtyPattern);
        regExMap.put(txtPackSize, packSizePattern);
        regExMap.put(txtUnitPrice,unitPricePattern);
        regExMap.put(txtDescription,descriptionPattern);
        regExMap.put(txtDiscount,discountPattern);
        regExMap.put(txtEveryItem,everyItemPattern);
        regExMap.put(txtMaxDiscount,maxDiscountPattern);

    }

    public void updateOnAction(ActionEvent event) {
        try {
            if (editItemDetailBO.updateItem(new ItemDTO(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    txtPackSize.getText(),
                    Integer.parseInt(txtQtyOnHand.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Double.parseDouble(txtDiscount.getText()),
                    Integer.parseInt(txtEveryItem.getText()),
                    Double.parseDouble(txtMaxDiscount.getText())
            ), actualId)) {
                System.out.println("Successfully Update");
                controller.loadItems();
            } else {
                System.out.println("Update error");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void sendData(ItemDTO itemDTO, ModifyItemFormController controller) {
        txtItemCode.setText(itemDTO.getItemCode());
        txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
        txtPackSize.setText(itemDTO.getPackSize());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        txtDescription.setText(itemDTO.getDescription());
        txtDiscount.setText(String.valueOf(itemDTO.getDiscount()));
        txtEveryItem.setText(String.valueOf(itemDTO.getEveryItem()));
        txtMaxDiscount.setText(String.valueOf(itemDTO.getMaxDiscount()));
        actualId = itemDTO.getItemCode();
        this.controller = controller;
    }

    public void validateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(regExMap,btnUpdate);

        if (keyEvent.getCode()== KeyCode.ENTER){
            ValidationUtil.requestFocus(regExMap,btnUpdate);
        }
    }
}
