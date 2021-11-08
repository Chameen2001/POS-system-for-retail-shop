package view.tableModel;

public class AddToCartTM {

    private String itemId;
    private String Description;
    private int Quantity;
    private double discount;
    private double price;
    private int qtyOnHand;
    private double percentage;
    private double unitPrice;

    public AddToCartTM() {
    }

    public AddToCartTM(String itemId, String description, int quantity, double discount, double price, int qtyOnHand, double percentage, double unitPrice) {
        this.itemId = itemId;
        Description = description;
        Quantity = quantity;
        this.discount = discount;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
        this.percentage = percentage;
        this.unitPrice = unitPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


}