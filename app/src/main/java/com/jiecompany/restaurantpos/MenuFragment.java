package com.jiecompany.restaurantpos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MenuFragment extends Fragment {
    private EditText search;
    private Button addButton;
    private Button mainMenu;
    private Button sideMenu;
    private Button setMenu;
    private Button liquorMenu;

    private ListView listView;

    private MenuListViewAdapter menuListViewAdapter;

    public MenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        search = view.findViewById(R.id.input_search);
        addButton = view.findViewById(R.id.menu_add_bt);

        listView = view.findViewById(R.id.menu_list);

        mainMenu = view.findViewById(R.id.menu_main_bt);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sideMenu = view.findViewById(R.id.menu_side_bt);
        setMenu = view.findViewById(R.id.menu_set_bt);
        liquorMenu = view.findViewById(R.id.menu_liquor_bt);

        //menuListViewAdapter = new MenuListViewAdapter(list, getContext());
        //listView.setAdapter(menuListViewAdapter);

        return view;
    }

}
