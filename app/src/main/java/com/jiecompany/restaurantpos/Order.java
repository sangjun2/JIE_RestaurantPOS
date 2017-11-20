package com.jiecompany.restaurantpos;

/**
 * Created by Sangjun on 2017-11-20.
 */

public class Order {
    public Menu menu;
    public int number;

    public Order() {

    }

    public Order(Menu menu, int number) {
        this.menu = menu;
        this.number = number;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
