package controller;

import bo.BOFactory;
import bo.custom.impl.RemoveItemBOImpl;
import dao.custom.impl.ItemDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.ItemDTO;
import view.tableModel.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RemoveItemFormController {
    public TableView <ItemTM>tblItems;
    public TableColumn clmCode;
    public TableColumn clmDescription;
    public TableColumn clmQtyOnHand;
    public TableColumn clmUnitPrize;
    public TableColumn clmDelete;
    public TextField txtSearch;
    private ObservableList<ItemTM> observableList;
    ArrayList<ItemDTO> allItemDTOS;

    private final RemoveItemBOImpl removeItemBO = (RemoveItemBOImpl) BOFactory.getInstance().getBOImpl(BOFactory.BOType.REMOVE_ITEM_BO);

    public void initialize(){

        clmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmUnitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        loadItems();

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
        allItemDTOS = removeItemBO.getItemLike(itemId);
        loadITable();
    }

    private void loadItems() {
        try {
            allItemDTOS = removeItemBO.getAllItems();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        loadITable();
    }

    private void loadITable() {

        observableList=FXCollections.observableArrayList();

        for (ItemDTO temp: allItemDTOS) {
            Button delete = new Button("Delete");
            observableList.add(new ItemTM(
                    temp.getItemCode(),
                    temp.getDescription(),
                    temp.getQtyOnHand(),
                    temp.getUnitPrice(),
                    delete
            ));

            delete.setOnAction(event -> {
                ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.NONE,"Do you want to Delete this??",no,yes);
                alert.setTitle("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.orElse(no)==yes) {

                    try {
                        if(removeItemBO.deleteItem(temp.getItemCode())){
                            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Removed Item",ButtonType.OK).show();
                            loadItems();
                        }else {
                            new Alert(Alert.AlertType.ERROR,"Remove Item Error",ButtonType.OK).show();
                        }
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });
        }

        tblItems.setItems(observableList);
    }

}
