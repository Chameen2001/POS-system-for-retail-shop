package view.tableModel;

import javafx.scene.control.Button;

public class ItemTM {
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
    private Button delete;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String description, int quantity, double unitPrice, Button delete) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = quantity;
        this.unitPrice = unitPrice;
        this.delete = delete;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return qtyOnHand;
    }

    public void setQuantity(int quantity) {
        this.qtyOnHand = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
