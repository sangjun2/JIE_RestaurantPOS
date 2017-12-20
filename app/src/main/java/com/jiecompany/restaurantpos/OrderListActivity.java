package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OrderListActivity extends AppCompatActivity {
    ListView order_list;
    OrderListAdapter order_list_adapter;
    int index;
    Button back;
    Button modify_menu;
    TextView total;
    public ArrayList<Order> orderList;
    int t;

    class OrderListAdapter extends BaseAdapter{
        ArrayList<Order> orders;
        LayoutInflater layoutInflater;


        public OrderListAdapter(LayoutInflater layoutInflater){
            ArrayList<Order> orders = SplashActivity.TABLE_LIST.get(String.valueOf(index)).getOrderList();
            this.orders = orders == null ? new ArrayList<Order>() : orders;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.order_item, null, false);

            int index = position;

            TextView menu =(TextView)view.findViewById(R.id.menu);
            menu.setText(orders.get(position).getMenu().getName());
            final TextView ea =(TextView)view.findViewById(R.id.ea);
            ea.setText(String.valueOf(orders.get(position).getNumber()));

            Button minus = (Button)view.findViewById(R.id.order_selected_minus);
            minus.setTag(index);
            Button plus = (Button) view.findViewById(R.id.order_selected_plus);
            plus.setTag(index);

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = Integer.parseInt(view.getTag().toString());
                    int num = Integer.parseInt(ea.getText().toString());
                    if(num == 1) {
                        ea.setText("1");
                    } else {
                        num--;
                        ea.setText(String.valueOf(num));
                        orders.get(index).setNumber(num);
                        t -= orders.get(index).getMenu().getPrice();
                    }
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = Integer.parseInt(view.getTag().toString());
                    int num = Integer.parseInt(ea.getText().toString());
                    num++;
                    ea.setText(String.valueOf(num));
                    orders.get(index).setNumber(num);
                    t += orders.get(index).getMenu().getPrice();
                }
            });

            total.setText("총액 : " + t);
            return view;
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        orderList = new ArrayList<>();
        total = (TextView) findViewById(R.id.total);

        back = (Button) findViewById(R.id.back);
        modify_menu = (Button) findViewById(R.id.modifyMenu);
        order_list = (ListView)findViewById(R.id.orderList);
        order_list_adapter = new OrderListAdapter(getLayoutInflater());

        for(int i = 0; i < order_list_adapter.orders.size(); i++){
            t+= order_list_adapter.orders.get(i).getMenu().getPrice() * order_list_adapter.orders.get(i).getNumber();
        }
        total.setText("총액 : " + t);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        modify_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                String randomKey;

                Map<String, Object> childUpdates = new HashMap<>();
                ArrayList<Order> orders;
                if(SplashActivity.TABLE_LIST.get(String.valueOf(index)) == null) {
                    orders = new ArrayList<>();
                    randomKey = ref.push().getKey();
                } else {
                    orders = SplashActivity.TABLE_LIST.get(String.valueOf(index)).getOrderList();
                    randomKey = SplashActivity.TABLE_LIST.get(String.valueOf(index)).getKey();
                }
                ArrayList<Order> result = new ArrayList<>();
                int t = 0;
                for(int i = 0; i < orders.size(); i++) {
                    result.add(orders.get(i));
                }
                orderList = order_list_adapter.orders;
                for(int i = 0; i < orderList.size(); i++) {
                    boolean exist = false;
                    for(int j = 0; j < result.size(); j++) {
                        if(result.get(j).getMenu().getKey().equals(orderList.get(i).getMenu().getKey())) {
                            result.get(j).setNumber(orderList.get(i).getNumber());
                            exist = true;
                            break;
                        }
                    }

                    if(!exist) {
                        result.add(orderList.get(i));
                    }
                    t += (orderList.get(i).getMenu().getPrice() * orderList.get(i).getNumber());
                }
                childUpdates.put("table/" + String.valueOf(index) + "/", new Table(randomKey, result, t).toMap());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
                SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");

                childUpdates.put("receipt/" + randomKey + "/", new Receipt(index, format.format(calendar.getTime()), t, new ArrayList<Payment>(), "").toMap());

                ref.updateChildren(childUpdates);

                finish();

            }
        });

        order_list.setAdapter(order_list_adapter);


    }
}