package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Sangjun on 2017-11-19.
 */

public class TableRecyclerViewAdapter extends RecyclerView.Adapter<TableRecyclerViewAdapter.ViewHolder> {
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.table_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Table table = SplashActivity.TABLE_LIST.get(String.valueOf(position));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        if(table == null) {
            table = new Table(String.valueOf(position), new ArrayList<Order>(), 0);
        }

        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.table_dialog, null);

                builder.setView(dialogView);

                TextView indexTextView = (TextView) dialogView.findViewById(R.id.dialog_title);
                Button orderButton = (Button) dialogView.findViewById(R.id.dialog_order_bt);
                Button orderListButton = (Button) dialogView.findViewById(R.id.dialog_order_list_bt);
                Button payButton = (Button) dialogView.findViewById(R.id.dialog_pay_bt);

                final AlertDialog dialog = builder.create();
                payButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(SplashActivity.TABLE_LIST.get(String.valueOf(position)) == null) {
                            dialog.dismiss();
                            Toast.makeText(mContext, "주문 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, PayActivity.class);
                            intent.putExtra("index", position);
                            mContext.startActivity(intent);
                        }
                    }
                });

                orderListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(SplashActivity.TABLE_LIST.get(String.valueOf(position)) == null) {
                            dialog.dismiss();
                            Toast.makeText(mContext, "주문 내역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, OrderListActivity.class);
                            intent.putExtra("index", position);
                            mContext.startActivity(intent);
                        }
                    }
                });

                indexTextView.setText(String.valueOf(position + 1));


                dialog.setCanceledOnTouchOutside(true);

                dialog.show();

                orderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(mContext, OrderActivity.class);
                        intent.putExtra("index", position);
                        mContext.startActivity(intent);
                    }
                });

                indexTextView.setText(String.valueOf(position + 1));
            };
        });

        holder.indexTextView.setText(String.valueOf(position + 1));
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < table.getOrderList().size(); i++) {
            buf.append(table.getOrderList().get(i).getMenu().getName() + " " + table.getOrderList().get(i).getNumber() + "\n");
        }
        holder.contentListView.setText(buf + "");
        holder.totalTextView.setText(String.valueOf(table.getTotal()) + " 원");
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View viewLayout;
        TextView indexTextView;
        TextView contentListView;
        TextView totalTextView;

        ViewHolder(View view) {
            super(view);

            viewLayout = (ConstraintLayout) view.findViewById(R.id.table_item);
            indexTextView = (TextView) view.findViewById(R.id.table_item_index);
            contentListView = (TextView) view.findViewById(R.id.table_item_content);
            totalTextView = (TextView) view.findViewById(R.id.table_item_total);
        }
    }
}
