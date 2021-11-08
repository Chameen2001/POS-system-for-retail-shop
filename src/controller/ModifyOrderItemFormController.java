package controller;

import bo.BOFactory;
import bo.custom.ModifyOrderItemBO;
import bo.custom.impl.ModifyOrderItemBOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import dto.OrderDetailDTO;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.ValidationUtil;
import view.tableModel.OrderDetailTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ModifyOrderItemFormController {


    public Label lblItemId;
    public TextField txtQuantity;
    public Button btnSave;
    String orderId;
    OrderDetailTM orderDetailTM;
    private Button btnDelete;
    private int oldQuantity;

    ModifyOrderItemBO modifyOrderItemBO = (ModifyOrderItemBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.MODIFY_ORDER_ITEM_BO);

    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");

    LinkedHashMap<TextField,Pattern> regExMap = new LinkedHashMap<>();

    public void initialize(){
        btnSave.setDisable(true);
        initializeRegExMap();
    }

    private void initializeRegExMap() {
        regExMap.put(txtQuantity,qtyPattern);
    }

    public void saveOnAction(ActionEvent event) {

        int changedQuantity = Integer.parseInt(txtQuantity.getText()) - oldQuantity;

        try {
            if (modifyOrderItemBO.saveChanges(new OrderDetailDTO(orderId,lblItemId.getText(), orderDetailTM.getQuantity()),changedQuantity)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Successfully Changes",ButtonType.OK).show();
                btnDelete.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Changes Unsuccessfully",ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void setData(OrderDetailTM orderDetailTM, String orderId, Button btnDelete) {
        this.orderDetailTM = orderDetailTM;
        lblItemId.setText(orderDetailTM.getItemCode());
        txtQuantity.setText(String.valueOf(orderDetailTM.getQuantity()));
        this.orderId = orderId;
        this.btnDelete = btnDelete;
        this.oldQuantity = orderDetailTM.getQuantity();
    }

    public void validateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(regExMap,btnSave);

        if (keyEvent.getCode()== KeyCode.ENTER) {
            ValidationUtil.requestFocus(regExMap,btnSave);
        }
    }
}
