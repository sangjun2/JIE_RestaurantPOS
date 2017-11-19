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

public class MenuListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Menu> list;

    public MenuListViewAdapter(ArrayList<Menu> list, Context context) {
        this.list = list;
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
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);

        TextView name = convertView.findViewById(R.id.menu_item_name);
        TextView price = convertView.findViewById(R.id.menu_item_price);
        Button modifyButton = convertView.findViewById(R.id.menu_item_modify);

        name.setText(list.get(i).getName());
        price.setText(String.valueOf(list.get(i).getPrice()));
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
