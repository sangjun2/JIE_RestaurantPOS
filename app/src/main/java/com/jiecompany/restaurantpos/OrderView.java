package com.jiecompany.restaurantpos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderView extends LinearLayout {
    TextView menu;
    TextView ea;
    public OrderView(Context context){
        super(context);
        init(context);
    }

    public OrderView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_order,this, true);

        menu = (TextView) view.findViewById(R.id.menu);
        ea = (TextView) view.findViewById(R.id.ea);
    }

    public TextView getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu.setText(menu);
    }

    public TextView getEa() {
        return ea;
    }

    public void setEa(int ea) {
        this.ea.setText(ea);
    }
}
