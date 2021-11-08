package dto;

public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private int quantity;
    private double discount;
    private double unitPrice;
    private double price;
    private double percentage;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemCode, int quantity) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public OrderDetailDTO(String orderId, String itemCode, int quantity, double discount, double unitPrice, double price, double percentage) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.price = price;
        this.percentage = percentage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}