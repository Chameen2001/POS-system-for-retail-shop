package controller;

import bo.BOFactory;
import bo.custom.ModifyItemBO;
import bo.custom.impl.ModifyItemBOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.ItemDTO;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyItemFormController {

    public TableView<ItemDTO> tblItems;
    public TableColumn clmCode;
    public TableColumn clmDescription;
    public TableColumn clmQtyOnHand;
    public TableColumn clmPackSize;
    public TableColumn clmUnitPrize;
    public TableColumn clmDiscount;
    public TableColumn clmEveryItem;
    public TableColumn clmMaxDiscount;
    public TextField txtSearch;

    ObservableList<ItemDTO> observableList;

    ModifyItemBO modifyItemBO = (ModifyItemBO) BOFactory.getInstance().getBOImpl(BOFactory.BOType.MODIFY_ITEM_BO);

    public void initialize(){
        clmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        clmUnitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        clmEveryItem.setCellValueFactory(new PropertyValueFactory<>("everyItem"));
        clmMaxDiscount.setCellValueFactory(new PropertyValueFactory<>("maxDiscount"));

        loadItems();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/EditItemDetailForm.fxml"));
                Parent load = fxmlLoader.load();
                EditItemDetailFormController editItemDetailFormController = fxmlLoader.getController();
                editItemDetailFormController.sendData(newValue,this);
                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.show();
            } catch (IOException | NullPointerException e) {

            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")){
                loadItems();
            }else {
                try {
                    loadItemsInSearch(newValue);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

    }

    private void loadItemsInSearch(String itemId) throws SQLException, ClassNotFoundException {
        observableList=FXCollections.observableArrayList(modifyItemBO.getItemLike(itemId));
        tblItems.setItems(observableList);
    }

    public void loadItems() {
        try {
            observableList = FXCollections.observableArrayList(modifyItemBO.getAllItems());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblItems.setItems(observableList);
    }

}
