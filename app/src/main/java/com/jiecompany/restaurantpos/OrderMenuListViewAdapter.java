package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-21.
 */

public class OrderMenuListViewAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Menu> list;
    public OrderListViewAdapter adapter;

    public OrderMenuListViewAdapter(ArrayList<Menu> list, Context context, OrderListViewAdapter adapter) {
        this.list = list;
        mContext = context;
        this.adapter = adapter;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);
        final TextView name = convertView.findViewById(R.id.menu_item_name);
        final TextView price = convertView.findViewById(R.id.menu_item_price);

        name.setText(list.get(i).getName());
        price.setText(String.valueOf(list.get(i).getPrice()) + " 원");

        Button button = convertView.findViewById(R.id.menu_item_bt);
        button.setTag(i);
        button.setText("추가하기");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = Integer.parseInt(view.getTag().toString());
                boolean exist = false;

                for(int i = 0; i < adapter.list.size(); i++) {
                    if(adapter.list.get(i).getMenu().getKey().equals(list.get(index).getKey())) {
                        exist = true;
                        break;
                    }
                }

                if(exist) {
                    Toast.makeText(mContext, "이미 존재하는 메뉴입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.list.add(new Order(list.get(index), 1));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }
}
