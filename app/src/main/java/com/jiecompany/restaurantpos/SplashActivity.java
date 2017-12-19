package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageView;
    public static Map<String, Menu> MENU_LIST;
    private long menuCount;
    private boolean completedLoadMenu;
    public static Map<String, Table> TABLE_LIST;
    public static Map<String, Receipt> RECEIPT_LIST;
    private long tableCount;
    private boolean completedLoadTable;
    private long receiptCount;
    private boolean completedReceipt;

    public static int NUMBER_OF_TABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.splash_progress);
        progressBar.setVisibility(View.VISIBLE);

        imageView = findViewById(R.id.splash_image);
        AssetManager am = getResources().getAssets();
        InputStream is = null;

        try {
            is = am.open("splash_image.png");
            Bitmap bm = BitmapFactory.decodeStream(is);

            imageView.setImageBitmap(bm);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = getSharedPreferences("Account", MODE_PRIVATE);
        int num = preferences.getInt("number", 6);

        NUMBER_OF_TABLE = num;

        MENU_LIST = new HashMap<>();
        menuCount = 0;
        completedLoadMenu = false;
        TABLE_LIST = new HashMap<>();
        tableCount = 0;
        completedLoadTable = false;
        RECEIPT_LIST = new HashMap<>();
        receiptCount = 0;
        completedReceipt = false;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference menuRef = database.getReference("menu");
        DatabaseReference tableRef = database.getReference("table");
        DatabaseReference receiptRef = database.getReference("receipt");

        menuRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MENU_LIST.put(dataSnapshot.getKey(), dataSnapshot.getValue(Menu.class));
                ++menuCount;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MENU_LIST.remove(dataSnapshot.getKey());
                MENU_LIST.put(dataSnapshot.getKey(), dataSnapshot.getValue(Menu.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MENU_LIST.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR==", databaseError.getMessage());
            }
        });
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(menuCount == dataSnapshot.getChildrenCount()) {
                    completedLoadMenu = true;
                }

                if(completedLoadMenu && completedLoadTable && completedReceipt) {
                    setPassword();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TABLE_LIST.put(String.valueOf(dataSnapshot.getKey()), dataSnapshot.getValue(Table.class));
                ++tableCount;
                if(MainFragment.adapter != null) {
                    MainFragment.adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                TABLE_LIST.remove(dataSnapshot.getKey());
                TABLE_LIST.put(String.valueOf(dataSnapshot.getKey()), dataSnapshot.getValue(Table.class));
                MainFragment.adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                TABLE_LIST.remove(dataSnapshot.getKey());
                MainFragment.adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(tableCount == dataSnapshot.getChildrenCount()) {
                    completedLoadTable = true;
                }

                if(completedLoadMenu && completedLoadTable && completedReceipt) {
                    setPassword();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        receiptRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RECEIPT_LIST.put(dataSnapshot.getKey(), dataSnapshot.getValue(Receipt.class));
                ++receiptCount;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RECEIPT_LIST.remove(dataSnapshot.getKey());
                RECEIPT_LIST.put(dataSnapshot.getKey(), dataSnapshot.getValue(Receipt.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                RECEIPT_LIST.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        receiptRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(receiptCount == dataSnapshot.getChildrenCount()) {
                    completedReceipt = true;
                }

                if(completedLoadMenu && completedLoadTable && completedReceipt) {
                    setPassword();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setPassword() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Account", MODE_PRIVATE);
        String password = preferences.getString("password", "");
        if(password.equals("")) {
            Intent intent = new Intent(SplashActivity.this, PasswordActivity.class);
            intent.putExtra("type", "set");
            startActivityForResult(intent, 0);
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0) {
            if(resultCode == 1) {
                boolean isTrue = data.getBooleanExtra("response", false);
                if(isTrue) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
