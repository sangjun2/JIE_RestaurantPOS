package com.jiecompany.restaurantpos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    ListView order_list;
    OrderListAdapter order_list_adapter;


    class OrderListAdapter extends BaseAdapter{
        ArrayList<Order> orders;
        LayoutInflater layoutInflater;

        public OrderListAdapter(LayoutInflater layoutInflater){
            this.orders = new ArrayList<>();
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int position) {
            return orders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.activity_order, null, false);
            TextView menu =(TextView)view.findViewById(R.id.menu);
            TextView ea =(TextView)view.findViewById(R.id.ea);

            menu.setText(orders.get(position).getMenu());
            ea.setText(orders.get(position).getEa());

            return view;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        order_list = (ListView)findViewById(R.id.orderList);
        order_list_adapter = new OrderListAdapter(getLayoutInflater());

        order_list.setAdapter(order_list_adapter);
    }
}
