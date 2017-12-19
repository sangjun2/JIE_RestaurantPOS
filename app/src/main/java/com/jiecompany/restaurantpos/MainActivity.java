package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    Intent intent;

    private static int REQUEST_MENU = 1;
    private static int REQUEST_ACCOUNTING = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_table:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, MainFragment.newInstance());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_menu:
                    intent.putExtra("type", "get");
                    startActivityForResult(intent, REQUEST_MENU);
                    return true;
                case R.id.navigation_accounting:
                    intent.putExtra("type", "get");
                    startActivityForResult(intent, REQUEST_ACCOUNTING);
                    return true;
                case R.id.navigation_setting:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, SettingFragment.newInstance());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }


    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean isTrue = false;

        if(requestCode == REQUEST_MENU) {
            if(resultCode == 1) {
                isTrue = data.getBooleanExtra("response", false);
                if(isTrue) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, MenuFragment.newInstance());
                    fragmentTransaction.commit();
                }
            }
        } else if(requestCode == REQUEST_ACCOUNTING) {
            if(resultCode == 1) {
                isTrue = data.getBooleanExtra("response", false);
                if(isTrue) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, AccountingFragment.newInstance());
                    fragmentTransaction.commit();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, PasswordActivity.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, MainFragment.newInstance());
        fragmentTransaction.commit();
    }

}
