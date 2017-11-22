package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity {
    private int index;
    private TextView totalValue;
    private EditText money;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mContext = this;

        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);

        final Receipt receipt = SplashActivity.RECEIPT_LIST.get(SplashActivity.TABLE_LIST.get(String.valueOf(index)).getKey());

        Button payButton = findViewById(R.id.pay_button);
        money = findViewById(R.id.pay_money);

        totalValue = findViewById(R.id.pay_total_money);
        totalValue.setText(String.valueOf(receipt.getTotal()) + " 원");

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money.getText().toString().equals("")) {
                    Toast.makeText(mContext, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(receipt.getTotal() - Integer.parseInt(money.getText().toString()) < 0) {
                    Toast.makeText(mContext, String.valueOf(receipt.getTotal()) + " 원 보다 작은 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);

                    View dialogView = LayoutInflater.from(PayActivity.this).inflate(R.layout.pay_dialog, null);


                    Button cashButton = (Button) dialogView.findViewById(R.id.dialog_cash_bt);
                    Button cardButton = (Button) dialogView.findViewById(R.id.dialog_card_bt);

                    builder.setView(dialogView);

                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(true);

                    dialog.show();

                    cashButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("receipt/" + SplashActivity.TABLE_LIST.get(String.valueOf(index)).getKey() + "/");
                            DatabaseReference tableRef = database.getReference("table/");

                            int total = 0;
                            String type = receipt.getType();
                            if(type.equals("")) {
                                type = "현금";
                            } else if(type.equals("현금")){

                            } else if(type.equals("카드")) {
                                type = "현금, 카드";
                            }

                            ArrayList<Payment> list = new ArrayList<>();
                            if(receipt.getPayList() == null) {
                            } else {
                                for(int i = 0; i < receipt.getPayList().size(); i++) {
                                    list.add(receipt.getPayList().get(i));
                                    total += receipt.getPayList().get(i).getMoney();
                                }
                            }
                            list.add(new Payment(Integer.parseInt(money.getText().toString()), "현금"));
                            total += Integer.parseInt(money.getText().toString());

                            Receipt update = new Receipt(receipt.getTableNo(), receipt.getReceiptNo(), receipt.getTotal(), list, type);
                            ref.updateChildren(update.toMap());

                            if(total == receipt.getTotal()) {
                                tableRef.child(String.valueOf(receipt.getTableNo())).setValue(null);
                                MainFragment.adapter.notifyDataSetChanged();
                            }

                            dialog.dismiss();
                            finish();
                        }
                    });

                    cardButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("receipt/" + SplashActivity.TABLE_LIST.get(String.valueOf(index)).getKey() + "/");
                            DatabaseReference tableRef = database.getReference("table/");

                            int total = 0;
                            String type = receipt.getType();
                            if(type.equals("")) {
                                type = "카드";
                            } else if(type.equals("카드")){

                            } else if(type.equals("현금")) {
                                type = "현금, 카드";
                            }

                            ArrayList<Payment> list = new ArrayList<>();
                            if(receipt.getPayList() == null) {
                            } else {
                                for(int i = 0; i < receipt.getPayList().size(); i++) {
                                    list.add(receipt.getPayList().get(i));
                                    total += receipt.getPayList().get(i).getMoney();
                                }
                            }
                            list.add(new Payment(Integer.parseInt(money.getText().toString()), "카드"));
                            total += Integer.parseInt(money.getText().toString());

                            Receipt update = new Receipt(receipt.getTableNo(), receipt.getReceiptNo(), receipt.getTotal(), list, type);
                            ref.updateChildren(update.toMap());

                            if(total == receipt.getTotal()) {
                                tableRef.child(String.valueOf(receipt.getTableNo())).setValue(null);
                                MainFragment.adapter.notifyDataSetChanged();
                            }

                            dialog.dismiss();
                            finish();
                        }
                    });



                }
            }
        });
    }
}
