package com.jiecompany.restaurantpos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-22.
 */

@IgnoreExtraProperties
public class Receipt {
    public int tableNo;
    public String receiptNo;
    public int total;
    public ArrayList<Payment> payList;
    public String type;

    public Receipt() {

    }

    public Receipt(int tableNo, String receiptNo, int total, ArrayList<Payment> list, String type) {
        this.tableNo = tableNo;
        this.receiptNo = receiptNo;
        this.total = total;
        payList = list;
        this.type = type;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("tableNo", tableNo);
        map.put("receiptNo", receiptNo);
        map.put("total", total);

        Map<String, Object> payMap = new HashMap<>();
        for(int i = 0; i < payList.size(); i++) {
            payMap.put(String.valueOf(i), payList.get(i).toMap());
        }

        map.put("payList", payMap);
        map.put("type", type);

        return map;
    }

    public ArrayList<Payment> getPayList() {
        return payList;
    }

    public void setPayList(ArrayList<Payment> payList) {
        this.payList = payList;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
