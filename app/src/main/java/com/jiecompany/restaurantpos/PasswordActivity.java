package com.jiecompany.restaurantpos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PasswordActivity extends AppCompatActivity {
    TextView title;
    TextView content;

    int index;
    String result;
    String type;

    ImageView index0;
    ImageView index1;
    ImageView index2;
    ImageView index3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        title = findViewById(R.id.password_title);
        content = findViewById(R.id.password_input_text);

        index = 0;
        result = "";

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(type.equals("set")) {
            title.setText("새로운 암호설정");
            content.setText("암호를 입력해주세요.");
        } else if(type.equals("get")) {
            title.setText("권한확인");
            content.setText("암호를 입력해주세요.");
        }

        Button zero = findViewById(R.id.password_num_0);
        zero.setOnClickListener(inputListener);
        Button one = findViewById(R.id.password_num_1);
        one.setOnClickListener(inputListener);
        Button two = findViewById(R.id.password_num_2);
        two.setOnClickListener(inputListener);
        Button three = findViewById(R.id.password_num_3);
        three.setOnClickListener(inputListener);
        Button four = findViewById(R.id.password_num_4);
        four.setOnClickListener(inputListener);
        Button five = findViewById(R.id.password_num_5);
        five.setOnClickListener(inputListener);
        Button six = findViewById(R.id.password_num_6);
        six.setOnClickListener(inputListener);
        Button seven = findViewById(R.id.password_num_7);
        seven.setOnClickListener(inputListener);
        Button eight = findViewById(R.id.password_num_8);
        eight.setOnClickListener(inputListener);
        Button nine = findViewById(R.id.password_num_9);
        nine.setOnClickListener(inputListener);
        Button remove = findViewById(R.id.password_num_remove);
        remove.setOnClickListener(inputListener);

        index0 = findViewById(R.id.password_index_0);
        index1 = findViewById(R.id.password_index_1);
        index2 = findViewById(R.id.password_index_2);
        index3 = findViewById(R.id.password_index_3);
    }

    View.OnClickListener inputListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String input = "";

            switch (view.getId()) {
                case R.id.password_num_0:
                    input = "0";
                    break;
                case R.id.password_num_1:
                    input = "1";
                    break;
                case R.id.password_num_2:
                    input = "2";
                    break;
                case R.id.password_num_3:
                    input = "3";
                    break;
                case R.id.password_num_4:
                    input = "4";
                    break;
                case R.id.password_num_5:
                    input = "5";
                    break;
                case R.id.password_num_6:
                    input = "6";
                    break;
                case R.id.password_num_7:
                    input = "7";
                    break;
                case R.id.password_num_8:
                    input = "8";
                    break;
                case R.id.password_num_9:
                    input = "9";
                    break;
                case R.id.password_num_remove:
                    break;
                default:
                    break;
            }

            if(input.equals("")) { // remove
                if(index > 0) {
                    result = result.substring(0, result.length() - 1);

                    if(index == 1) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index0.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        } else {
                            index0.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        }
                    } else if(index == 2) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        } else {
                            index1.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        }
                    } else if(index == 3) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        } else {
                            index2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                        }
                    }

                    index--;
                }
            } else {
                if(index < 3) { // more input
                    result += input;
                    if(index == 0) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index0.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        } else {
                            index0.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        }
                    } else if(index == 1) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        } else {
                            index1.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        }
                    } else if(index == 2) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            index2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        } else {
                            index2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                        }
                    }

                    index++;
                } else { // verify

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        index3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                    } else {
                        index3.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_black_24dp));
                    }
                    SharedPreferences preferences = getSharedPreferences("Account", MODE_PRIVATE);

                    if(type.equals("set")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("password", result);
                        editor.commit();

                        Intent intent = new Intent();
                        intent.putExtra("response", true);
                        setResult(1, intent);
                        finish();
                    } else if(type.equals("get")) {
                        String pw = preferences.getString("password", "");
                        if(!pw.equals("")) {
                            if(pw.equals(result)) {
                                Intent intent = new Intent();
                                intent.putExtra("response", true);
                                setResult(1, intent);
                                finish();
                            } else {
                                content.setText("암호가 일치하지 않습니다.");
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    index0.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                } else {
                                    index0.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index1.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                    index3.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white_24dp));
                                }

                                index = 0;
                                result = "";
                            }
                        }
                    }
                }
            }
        }
    };
}
