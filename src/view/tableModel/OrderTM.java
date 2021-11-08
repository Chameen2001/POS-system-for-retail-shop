package view.tableModel;

import javafx.scene.control.Button;

public class OrderTM {
    private String custId;
    private String orderId;
    private String date;
    private double cost;
    private String time;
    private Button deleteButton;

    public OrderTM() {
    }

    public OrderTM(String orderId, String date, double cost, String time) {
        this.orderId = orderId;
        this.date = date;
        this.cost = cost;
        this.time = time;
    }

    public OrderTM(String custId, String orderId, String date, double cost, String time) {
        this.custId = custId;
        this.orderId = orderId;
        this.date = date;
        this.cost = cost;
        this.time = time;
    }

    public OrderTM(String custId, String orderId, String date, double cost, String time, Button deleteButton) {
        this.custId = custId;
        this.orderId = orderId;
        this.date = date;
        this.cost = cost;
        this.time = time;
        this.deleteButton = deleteButton;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
