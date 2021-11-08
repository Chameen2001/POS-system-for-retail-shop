package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String customerId;
    private String orderId;
    private String orderDate;
    private double cost;
    private String orderTime;
    
    private ArrayList<OrderDetailDTO> orderDetailDTOS;

    public OrderDTO() {

    }

    public OrderDTO(String customerId, String orderId, String orderDate, double cost, String orderTime) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cost = cost;
        this.orderTime = orderTime;
    }

    public OrderDTO(String customerId, String orderId, String orderDate, double cost, String orderTime, ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cost = cost;
        this.orderTime = orderTime;
        this.orderDetailDTOS = orderDetailDTOS;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public ArrayList<OrderDetailDTO> getOrderDetailDTOS() {
        return orderDetailDTOS;
    }

    public void setOrderDetailDTOS(ArrayList<OrderDetailDTO> orderDetailDTOS) {
        this.orderDetailDTOS = orderDetailDTOS;
    }
}