package adapters;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Item {
    private String name;
    private String itemid;


    private String barcode;


    private String thumbnail;

    private String uom;


    private  String itemdes;

    public Item() {
    }

    public Item(String name, String itemid, String barcode, String thumbnail, String uom, String itemdes) {
        this.name = name;
        this.itemid = itemid;
        this.barcode = barcode;
        this.thumbnail = thumbnail;
        this.uom = uom;
        this.itemdes = itemdes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getItemdes() {
        return itemdes;
    }

    public void setItemdes(String itemdes) {
        this.itemdes = itemdes;
    }
}