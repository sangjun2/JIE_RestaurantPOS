package com.jiecompany.restaurantpos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class SettingFragment extends Fragment {

    public int REQUEST_PASSWORD_VERIFY = 0;
    public int REQUEST_PASSWORD_MODIFY = 1;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();

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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        LinearLayout tableNumberLayout = view.findViewById(R.id.setting_modify_table_number);
        LinearLayout passwordLayout = view.findViewById(R.id.setting_modify_password);

        tableNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        passwordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PasswordActivity.class);
                intent.putExtra("type", "get");
                startActivityForResult(intent, REQUEST_PASSWORD_VERIFY);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PASSWORD_VERIFY) {
            if(resultCode == 1) {
                boolean isTrue = data.getBooleanExtra("response", false);
                if(isTrue) {
                    Intent intent = new Intent(getContext(), PasswordActivity.class);
                    intent.putExtra("type", "set");
                    startActivityForResult(intent, REQUEST_PASSWORD_MODIFY);
                }
            }
        } else if(requestCode == REQUEST_PASSWORD_MODIFY) {
            if(resultCode == 1) {
                boolean isTrue = data.getBooleanExtra("response", false);
                if(isTrue) {
                    Toast.makeText(getContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
