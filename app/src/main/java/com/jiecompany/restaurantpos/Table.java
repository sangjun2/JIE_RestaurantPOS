package com.jiecompany.restaurantpos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-20.
 */

@IgnoreExtraProperties
public class Table {
    public String key;
    public ArrayList<Order> orderList;
    public int total;

    public Table() {

    }

    public Table(String key, ArrayList<Order> orderList, int total) {
        this.key = key;
        this.orderList = orderList;
        this.total = total;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        Map<String, Object> list = new HashMap<>();
        for(int i = 0; i < orderList.size(); i++) {
            list.put(String.valueOf(i), orderList.get(i).toMap());
        }

        map.put("orderList", list);
        map.put("total", total);

        return map;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
