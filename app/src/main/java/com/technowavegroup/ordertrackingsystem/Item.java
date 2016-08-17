package com.technowavegroup.ordertrackingsystem;

/**
 * Created by technoway on 5/9/2016.
 */
public class Item {


    private String Name;
    private String quanity;
    private String price;



    public Item(){

    }

    public Item(String name, String quanity, String price) {
        Name = name;
        this.quanity = quanity;
        this.price = price;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
