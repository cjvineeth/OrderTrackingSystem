package adapters;

/**
 * Created by vineeth on 6/29/2016.
 */
public class Order {

    String itemname;
    String shopname;
    String quantity;
    String orderno;


    public Order() {

    }

    public Order(String itemname, String shopname, String quantity, String orderno) {
        this.itemname = itemname;
        this.shopname = shopname;
        this.quantity = quantity;
        this.orderno = orderno;
    }


    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}