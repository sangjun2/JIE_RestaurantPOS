package com.jiecompany.restaurantpos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
            View view = layoutInflater.inflate(R.layout.order_item, null, false);
            TextView menu =(TextView)view.findViewById(R.id.menu);
            TextView ea =(TextView)view.findViewById(R.id.ea);

            menu.setText(orders.get(position).getMenu().getName());
            ea.setText(String.valueOf(orders.get(position).getNumber()));

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
        order_list_adapter.notifyDataSetChanged();
        addAllItems();

    }

    public void addAllItems(){
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mConditionRef = mdatabase.child("table/0/");//테이블번호

        mConditionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum = 0;
                TextView textView = findViewById(R.id.total);
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Menu menu = snapshot.child("menu").getValue(Menu.class);
                    int number = Integer.parseInt(snapshot.child("number").getValue().toString());
                    int one_order = number * menu.getPrice();
                    sum += one_order;
                    Order order = new Order(menu, number);
                    order_list_adapter.orders.add(order);
                    order_list_adapter.notifyDataSetChanged();
                }
                textView.setText(textView.getText().toString() + "   " +  sum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}