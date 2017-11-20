package com.jiecompany.restaurantpos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
