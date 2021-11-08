package view.tableModel;

public class OrderDetailTM {
    private String itemCode;
    private String description;
    private int quantity;
    private double discount;
    private double unitPrice;
    private double price;
    private double percentage;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String itemCode, int quantity, double discount, double unitPrice, double price, double percentage) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.price = price;
        this.percentage = percentage;
    }

    public OrderDetailTM(String itemCode, String description, int quantity) {
        this.itemCode = itemCode;
        this.description = description;
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
