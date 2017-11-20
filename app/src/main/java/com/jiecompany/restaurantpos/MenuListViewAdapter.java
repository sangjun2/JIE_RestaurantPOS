package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sangjun on 2017-11-20.
 */

public class MenuListViewAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Menu> list;

    public MenuListViewAdapter(ArrayList<Menu> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);

        final TextView name = convertView.findViewById(R.id.menu_item_name);
        final TextView price = convertView.findViewById(R.id.menu_item_price);
        Button modifyButton = convertView.findViewById(R.id.menu_item_modify);
        modifyButton.setText("수정하기");

        name.setText(list.get(i).getName());
        price.setText(String.valueOf(list.get(i).getPrice()));
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("메뉴 수정");
                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.menu_add, null);

                final EditText nameEdit = dialogView.findViewById(R.id.menu_add_name);
                final EditText priceEdit = dialogView.findViewById(R.id.menu_add_price);
                final Spinner spinner = dialogView.findViewById(R.id.menu_add_group);

                nameEdit.setText(name.getText().toString());
                priceEdit.setText(price.getText().toString());

                String[] groupArray = mContext.getResources().getStringArray(R.array.group_array);
                for(int j = 0; j < groupArray.length; j++) {
                    if(groupArray[j].equals(list.get(i).getGroup())) {
                        spinner.setSelection(j);
                    }
                }

                builder.setView(dialogView);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTag(i);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = Integer.parseInt(view.getTag().toString());

                        if(nameEdit.getText().toString().equals("") || priceEdit.getText().toString().equals("")) {
                            Toast.makeText(mContext, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            String key = list.get(index).getKey();

                            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference ref = mDatabase.getReference("menu");
                            Map<String, Object> childUpdates = new HashMap<>();

                            Menu menu = new Menu(key, spinner.getSelectedItem().toString(), nameEdit.getText().toString(), Integer.parseInt(priceEdit.getText().toString()));
                            for(int j = 0; j < SplashActivity.MENU_LIST.size(); j++) {
                                if(SplashActivity.MENU_LIST.get(j).getKey().equals(key)) {
                                    SplashActivity.MENU_LIST.remove(j);
                                    SplashActivity.MENU_LIST.add(j, menu);
                                    break;
                                }
                            }

                            childUpdates.put(key, menu.toMap());

                            ref.updateChildren(childUpdates);

                            list.remove(index);
                            list.add(index, menu);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return convertView;
    }
}
