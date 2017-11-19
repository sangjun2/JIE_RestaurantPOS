package com.jiecompany.restaurantpos;

/**
 * Created by Sangjun on 2017-11-20.
 */

public class Menu {
    public String group;
    public String name;
    public int price;

    public Menu() {

    }

    public Menu(String group, String name, int price) {
        this.group = group;
        this.name = name;
        this.price = price;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
