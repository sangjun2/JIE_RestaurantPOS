package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Button payButton = findViewById(R.id.pay_button);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pay_dialog, null);

                builder.setView(dialogView);

                Button cashButton = (Button) dialogView.findViewById(R.id.dialog_cash_bt);
                Button cardButton = (Button) dialogView.findViewById(R.id.dialog_card_bt);

                cashButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                cardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);

                dialog.show();
            }
        });
    }
}
