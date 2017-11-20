package com.jiecompany.restaurantpos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MenuFragment extends Fragment {
    private EditText search;
    private Button addButton;
    private Button mainMenu;
    private Button sideMenu;
    private Button setMenu;
    private Button liquorMenu;

    private ListView listView;

    private MenuListViewAdapter menuListViewAdapter;
    private MenuListViewAdapter sideListViewAdapter;
    private MenuListViewAdapter setListViewAdapter;
    private MenuListViewAdapter liquorListViewAdapter;

    ArrayList<Menu> mainList;
    ArrayList<Menu> sideList;
    ArrayList<Menu> setList;
    ArrayList<Menu> liquorList;

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

        mainList = new ArrayList<>();
        sideList = new ArrayList<>();
        setList = new ArrayList<>();
        liquorList = new ArrayList<>();

        menuListViewAdapter = new MenuListViewAdapter(mainList, getContext());
        sideListViewAdapter = new MenuListViewAdapter(sideList, getContext());
        setListViewAdapter = new MenuListViewAdapter(setList, getContext());
        liquorListViewAdapter = new MenuListViewAdapter(liquorList, getContext());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        search = view.findViewById(R.id.input_search);
        addButton = view.findViewById(R.id.menu_add_bt);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.menu_add, null);
                final EditText name = dialogView.findViewById(R.id.menu_add_name);
                final EditText price = dialogView.findViewById(R.id.menu_add_price);
                final AppCompatSpinner spinner = dialogView.findViewById(R.id.menu_add_group);

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

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name.getText().toString().equals("") || price.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference ref = mDatabase.getReference("menu");
                            Map<String, Object> childUpdates = new HashMap<>();

                            Menu menu = new Menu(spinner.getSelectedItem().toString(), name.getText().toString(), Integer.parseInt(price.getText().toString()));

                            String randomKey = ref.push().getKey();

                            childUpdates.put(randomKey, menu.toMap());

                            ref.updateChildren(childUpdates);

                            String type = spinner.getSelectedItem().toString();
                            if(type.equals("주메뉴")) {
                                mainList.add(menu);
                                menuListViewAdapter.list.add(menu);
                                menuListViewAdapter.notifyDataSetChanged();
                            } else if(type.equals("사이드메뉴")) {
                                sideList.add(menu);
                                sideListViewAdapter.list.add(menu);
                                sideListViewAdapter.notifyDataSetChanged();
                            } else if (type.equals("세트메뉴")) {
                                setList.add(menu);
                                setListViewAdapter.list.add(menu);
                                setListViewAdapter.notifyDataSetChanged();
                            } else if (type.equals("주류")) {
                                liquorList.add(menu);
                                liquorListViewAdapter.list.add(menu);
                                liquorListViewAdapter.notifyDataSetChanged();
                            } else {

                            }

                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        listView = view.findViewById(R.id.menu_list);

        searchListData(mainList, "주메뉴");
        listView.setAdapter(menuListViewAdapter);

        mainMenu = view.findViewById(R.id.menu_main_bt);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainList = new ArrayList<>();
                searchListData(mainList, "주메뉴");
                listView.setAdapter(menuListViewAdapter);
            }
        });
        sideMenu = view.findViewById(R.id.menu_side_bt);
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideList = new ArrayList<>();
                searchListData(sideList, "사이드메뉴");
                listView.setAdapter(sideListViewAdapter);
            }
        });
        setMenu = view.findViewById(R.id.menu_set_bt);
        setMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setList = new ArrayList<>();
                searchListData(setList, "세트메뉴");
                listView.setAdapter(setListViewAdapter);
            }
        });
        liquorMenu = view.findViewById(R.id.menu_liquor_bt);
        liquorMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liquorList = new ArrayList<>();
                searchListData(liquorList, "주류");
                listView.setAdapter(liquorListViewAdapter);
            }
        });

        return view;
    }

    private void searchListData(ArrayList<Menu> list, String type) {
        for(int i = 0; i < SplashActivity.MENU_LIST.size(); i++) {
            if(SplashActivity.MENU_LIST.get(i).getGroup().equals(type)) {
                list.add(SplashActivity.MENU_LIST.get(i));
            }
        }
    }

}
