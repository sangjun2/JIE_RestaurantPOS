package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OrderActivity extends AppCompatActivity {
    private Button mainMenu;
    private Button sideMenu;
    private Button setMenu;
    private Button liquorMenu;

    private ListView menuListView;
    private ListView orderListView;

    ArrayList<Menu> mainList;
    ArrayList<Menu> sideList;
    ArrayList<Menu> setList;
    ArrayList<Menu> liquorList;

    private int tableNumber;
    public static TextView totalValue;
    public static int TOTAL;
    
    public ArrayList<Order> orderList;

    private Button completeButton;

    OrderListViewAdapter orderListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        if(index != -1) {
            tableNumber = index;
        }

        TOTAL = 0;
        
        orderList = new ArrayList<>();

        mainMenu = findViewById(R.id.order_main_bt);
        sideMenu = findViewById(R.id.order_side_bt);
        setMenu = findViewById(R.id.order_set_bt);
        liquorMenu = findViewById(R.id.order_liquor_bt);

        totalValue = findViewById(R.id.order_total_value);
        totalValue.setText(TOTAL + " 원");

        mainList = new ArrayList<>();
        sideList = new ArrayList<>();
        setList = new ArrayList<>();
        liquorList = new ArrayList<>();

        orderListView = findViewById(R.id.order_selected_list);
        orderListViewAdapter = new OrderListViewAdapter(this);
        orderListView.setAdapter(orderListViewAdapter);

        menuListView = findViewById(R.id.order_menu_list);
        searchListData(mainList, "주메뉴");
        OrderMenuListViewAdapter mainListViewAdapter = new OrderMenuListViewAdapter(mainList, this, orderListViewAdapter);
        menuListView.setAdapter(mainListViewAdapter);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainList = new ArrayList<>();
                searchListData(mainList, "주메뉴");
                OrderMenuListViewAdapter mainListViewAdapter = new OrderMenuListViewAdapter(mainList, getApplicationContext(), orderListViewAdapter);
                menuListView.setAdapter(mainListViewAdapter);
            }
        });

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideList = new ArrayList<>();
                searchListData(sideList, "사이드메뉴");
                OrderMenuListViewAdapter sideListViewAdapter = new OrderMenuListViewAdapter(sideList, getApplicationContext(), orderListViewAdapter);
                menuListView.setAdapter(sideListViewAdapter);
            }
        });

        setMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setList = new ArrayList<>();
                searchListData(setList, "세트메뉴");
                OrderMenuListViewAdapter setListViewAdapter = new OrderMenuListViewAdapter(setList, getApplicationContext(), orderListViewAdapter);
                menuListView.setAdapter(setListViewAdapter);
            }
        });

        liquorMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liquorList = new ArrayList<>();
                searchListData(liquorList, "주류");
                OrderMenuListViewAdapter liquorListViewAdapter = new OrderMenuListViewAdapter(liquorList, getApplicationContext(), orderListViewAdapter);
                menuListView.setAdapter(liquorListViewAdapter);
            }
        });

        completeButton = findViewById(R.id.order_complete_bt);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                String randomKey;

                Map<String, Object> childUpdates = new HashMap<>();
                ArrayList<Order> orders;
                if(SplashActivity.TABLE_LIST.get(String.valueOf(tableNumber)) == null) {
                    orders = new ArrayList<>();
                    randomKey = ref.push().getKey();
                } else {
                    orders = SplashActivity.TABLE_LIST.get(String.valueOf(tableNumber)).getOrderList();
                    randomKey = SplashActivity.TABLE_LIST.get(String.valueOf(tableNumber)).getKey();
                }
                ArrayList<Order> result = new ArrayList<>();
                int t = 0;
                for(int i = 0; i < orders.size(); i++) {
                    result.add(orders.get(i));
                    t += (orders.get(i).getMenu().getPrice() * orders.get(i).getNumber());
                }
                orderList = orderListViewAdapter.list;
                for(int i = 0; i < orderList.size(); i++) {
                    boolean exist = false;
                    for(int j = 0; j < result.size(); j++) {
                        if(result.get(j).getMenu().getKey().equals(orderList.get(i).getMenu().getKey())) {
                            result.get(j).setNumber(result.get(j).getNumber() + orderList.get(i).getNumber());
                            exist = true;
                            break;
                        }
                    }

                    if(!exist) {
                        result.add(orderList.get(i));
                    }
                    t += (orderList.get(i).getMenu().getPrice() * orderList.get(i).getNumber());
                }
                childUpdates.put("table/" + String.valueOf(tableNumber) + "/", new Table(randomKey, result, t).toMap());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
                SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");


                childUpdates.put("receipt/" + randomKey + "/", new Receipt(tableNumber, format.format(calendar.getTime()), t, new ArrayList<Payment>(), "").toMap());

                ref.updateChildren(childUpdates);

                finish();
            }
        });

    }

    private void searchListData(ArrayList<Menu> list, String type) {
        for(Map.Entry<String, Menu> entry : SplashActivity.MENU_LIST.entrySet()) {
            if(entry.getValue().getGroup().equals(type)) {
                list.add(entry.getValue());
            }
        }
    }

    public static void update() {
        totalValue.setText(TOTAL + " 원");
    }

    @Override
    public void onBackPressed() {
        orderListViewAdapter.list = new ArrayList<>();
        super.onBackPressed();
    }
}
