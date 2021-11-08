package entity;

public class Order {
    private String custId;
    private String orderId;
    private String orderDate;
    private double cost;
    private String OrderTime;

    public Order() {
    }

    public Order(double cost, String orderTime) {
        this.cost = cost;
        OrderTime = orderTime;
    }

    public Order(String orderDate, double cost) {
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Order(String custId, String orderId, String orderDate, double cost, String OrderTime) {
        this.setCustId(custId);
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCost(cost);
        this.setOrderTime(OrderTime);
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
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        this.OrderTime = orderTime;
    }
}
