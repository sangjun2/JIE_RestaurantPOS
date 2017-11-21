package com.jiecompany.restaurantpos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-11-20.
 */

public class OrderListViewAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Order> list;

    public OrderListViewAdapter(Context context) {
        this.list = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View orderItemView = LayoutInflater.from(mContext).inflate(R.layout.order_selected_list_item, null);

        int index = i;

        Button removeButton = orderItemView.findViewById(R.id.order_selected_remove_bt);
        removeButton.setTag(index);
        TextView name = orderItemView.findViewById(R.id.order_selected_name);
        Button minusButton = orderItemView.findViewById(R.id.order_selected_minus);
        minusButton.setTag(index);
        final TextView number = orderItemView.findViewById(R.id.order_selected_number);
        number.setText(String.valueOf(list.get(index).getNumber()));
        Button plusButton = orderItemView.findViewById(R.id.order_selected_plus);
        plusButton.setTag(index);

        final Menu menu = this.list.get(i).getMenu();

        name.setText(menu.getName());
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = Integer.parseInt(view.getTag().toString());
                list.remove(index);
                notifyDataSetChanged();

                updateTotal();
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = Integer.parseInt(view.getTag().toString());
                int num = Integer.parseInt(number.getText().toString());
                if(num == 1) {
                    number.setText("1");
                } else {
                    num--;
                    number.setText(String.valueOf(num));
                    list.get(index).setNumber(num);
                }

                updateTotal();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = Integer.parseInt(view.getTag().toString());
                int num = Integer.parseInt(number.getText().toString());
                num++;
                number.setText(String.valueOf(num));
                list.get(index).setNumber(num);
                updateTotal();
            }
        });

        return orderItemView;
    }

    public void updateTotal() {
        int total = 0;

        for(int i = 0; i < this.list.size(); i++) {
            total += (this.list.get(i).getMenu().getPrice() * this.list.get(i).getNumber());
        }

        OrderActivity.TOTAL = total;
        OrderActivity.update();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateTotal();
    }
}
