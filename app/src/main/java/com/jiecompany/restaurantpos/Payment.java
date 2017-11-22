package com.jiecompany.restaurantpos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-22.
 */

@IgnoreExtraProperties
public class Payment {
    public int money;
    public String type;

    public Payment() {

    }

    public Payment(int money, String type) {
        this.money = money;
        this.type = type;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("type", type);

        return map;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
