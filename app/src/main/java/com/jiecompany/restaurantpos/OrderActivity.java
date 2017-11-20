package com.jiecompany.restaurantpos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class OrderActivity extends AppCompatActivity {
    private Button mainMenu;
    private Button sideMenu;
    private Button setMenu;
    private Button liquorMenu;

    private ListView menuListView;
    private ListView orderListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }
}
