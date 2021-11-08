package dto;

public class ItemDTO {

    private String itemCode;
    private String description;
    private String packSize;
    private int qtyOnHand;
    private double unitPrice;
    private double discount;
    private int everyItem;
    private double maxDiscount;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description, String packSize, int qtyOnHand, double unitPrice, double discount, int everyItem, double maxDiscount) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.everyItem = everyItem;
        this.maxDiscount = maxDiscount;
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

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getEveryItem() {
        return everyItem;
    }

    public void setEveryItem(int everyItem) {
        this.everyItem = everyItem;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
