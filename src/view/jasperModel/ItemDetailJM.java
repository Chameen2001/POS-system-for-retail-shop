package view.jasperModel;

public class ItemDetailJM {
    private String itemName;
    private String qty;
    private String pricePerQty;
    private String price;

    public ItemDetailJM() {
    }

    public ItemDetailJM(String itemName, String qty, String pricePerQty, String price) {
        this.itemName = itemName;
        this.qty = qty;
        this.pricePerQty = pricePerQty;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPricePerQty() {
        return pricePerQty;
    }

    public void setPricePerQty(String pricePerQty) {
        this.pricePerQty = pricePerQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
