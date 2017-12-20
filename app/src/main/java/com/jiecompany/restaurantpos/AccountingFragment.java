package com.jiecompany.restaurantpos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountingFragment extends Fragment {
    private Button totalListButton;
    private Button cashListButton;
    private Button cardListButton;

    private ListView listView;
    private AccountingListViewAdapter adapter;

    public static TextView TOTAL_VALUE;
    public static int TOTAL;

    public AccountingFragment() {
        // Required empty public constructor
    }


    public static AccountingFragment newInstance() {
        AccountingFragment fragment = new AccountingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounting, container, false);

        totalListButton = view.findViewById(R.id.accounting_total_list_bt);
        cashListButton = view.findViewById(R.id.accounting_total_cash_bt);
        cardListButton = view.findViewById(R.id.accounting_total_card_bt);
        TOTAL_VALUE = view.findViewById(R.id.accounting_total_value);

        listView = view.findViewById(R.id.accounting_list);
        adapter = new AccountingListViewAdapter(getContext(), null);
        listView.setAdapter(adapter);

        totalListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new AccountingListViewAdapter(getContext(), null);
                listView.setAdapter(adapter);
            }
        });

        cashListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Receipt> list = new HashMap<>();
                searchListData(list, "현금");
                adapter = new AccountingListViewAdapter(getContext(), list);
                listView.setAdapter(adapter);
            }
        });

        cardListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Receipt> list = new HashMap<>();
                searchListData(list, "카드");
                adapter = new AccountingListViewAdapter(getContext(), list);
                listView.setAdapter(adapter);
            }
        });

        return view;
    }

    public static void update() {
        TOTAL_VALUE.setText(TOTAL + " 원");
    }

    public void searchListData(Map<String, Receipt> list, String type) {
        for(Map.Entry<String, Receipt> entry : SplashActivity.RECEIPT_LIST.entrySet()) {
            if(entry.getValue().getType().contains(type)) {
                list.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
