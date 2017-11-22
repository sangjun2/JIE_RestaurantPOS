package com.jiecompany.restaurantpos;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-22.
 */

public class AccountingListViewAdapter extends BaseAdapter {
    private Context mContext;
    private Map<String, Receipt> list;
    private ArrayList<Receipt> arrayList;

    public AccountingListViewAdapter(Context context, @Nullable Map<String, Receipt> list) {
        mContext = context;
        if(list == null) {
            this.list = SplashActivity.RECEIPT_LIST;
        } else {
            this.list = list;
        }

        arrayList = new ArrayList<>();
        setSortList();

        AccountingFragment.TOTAL = calculateTotal();
        AccountingFragment.update();
    }

    @Override
    public int getCount() {
        return 1 + this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i == 0 ? null : this.arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.accounting_item, null);

        TextView tableNo = convertView.findViewById(R.id.accounting_item_table_no);
        TextView receiptNo = convertView.findViewById(R.id.accounting_item_receipt_no);
        TextView total = convertView.findViewById(R.id.accounting_item_total);
        TextView type = convertView.findViewById(R.id.accounting_item_type);

        if(i == 0) {
            tableNo.setBackgroundColor(Color.parseColor("#999999"));
            receiptNo.setBackgroundColor(Color.parseColor("#999999"));
            total.setBackgroundColor(Color.parseColor("#999999"));
            type.setBackgroundColor(Color.parseColor("#999999"));
        } else {
            Receipt receipt = this.arrayList.get(i - 1);

            tableNo.setText("no." + String.valueOf(receipt.getTableNo() + 1));
            receiptNo.setText(receipt.getReceiptNo());
            total.setText(String.valueOf(receipt.getTotal()) + " 원");
            type.setText(receipt.getType());
        }

        return convertView;
    }

    private void setSortList() {
        for(Map.Entry<String, Receipt> entry : this.list.entrySet()) {
            int total = 0;
            if(entry.getValue().getPayList() != null) {
                for(int i = 0; i < entry.getValue().getPayList().size(); i++) {
                    total += entry.getValue().getPayList().get(i).getMoney();
                }
            }

            if(entry.getValue().getTotal() == total) {
                this.arrayList.add(entry.getValue());
            }
        }

        Comparator<Receipt> comparator = new Comparator<Receipt>() {
            @Override
            public int compare(Receipt receipt, Receipt t1) {
                if(Integer.parseInt(receipt.getReceiptNo()) > Integer.parseInt(t1.getReceiptNo())) {
                    return 1;
                } else if(Integer.parseInt(receipt.getReceiptNo()) < Integer.parseInt(t1.getReceiptNo())) {
                    return -1;
                }
                return 0;
            }
        };

        Collections.sort(this.arrayList, comparator);
    }

    private int calculateTotal() {
        int total = 0;

        for(int i = 0; i < this.arrayList.size(); i++) {
            total += this.arrayList.get(i).getTotal();
        }

        return total;
    }
}
