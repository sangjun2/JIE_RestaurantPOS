package com.jiecompany.restaurantpos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-20.
 */

@IgnoreExtraProperties
public class Order {
    public Menu menu;
    public int number;

    public Order() {

    }

    public Order(Menu menu, int number) {
        this.menu = menu;
        this.number = number;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("menu", menu.toMap());
        map.put("number", number);

        return map;
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
