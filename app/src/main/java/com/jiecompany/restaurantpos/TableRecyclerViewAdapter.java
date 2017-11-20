package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.table_dialog, null);

                builder.setView(dialogView);

                TextView indexTextView = (TextView) dialogView.findViewById(R.id.dialog_title);
                Button orderButton = (Button) dialogView.findViewById(R.id.dialog_order_bt);
                Button orderListButton = (Button) dialogView.findViewById(R.id.dialog_order_list_bt);
                Button payButton = (Button) dialogView.findViewById(R.id.dialog_pay_bt);

                orderListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, OrderListActivity.class);
                        mContext.startActivity(intent);
                    }
                });

                indexTextView.setText(String.valueOf(position + 1));

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);

                dialog.show();
            }
        });

        holder.indexTextView.setText(String.valueOf(position + 1));

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
