package com.jiecompany.restaurantpos;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-11-20.
 */

public class Table {
    public int index;
    public ArrayList<Order> orderList;
    public int total;

    public Table() {

    }

    public Table(int index, ArrayList<Order> orderList, int total) {
        this.index = index;
        this.orderList = orderList;
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
