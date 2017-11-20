package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
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

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageView;
    public static ArrayList<Menu> MENU_LIST;

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

        MENU_LIST = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("menu");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Menu menu = snapshot.getValue(Menu.class);
                    MENU_LIST.add(menu);
                }

                progressBar.setVisibility(View.GONE);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
