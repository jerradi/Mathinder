package com.undersnow.mathinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by dc on 6/9/2017.
 */

public    class ViewHolder {
    TextView tvName;
    Question item;


    @Override
    public String toString() {
        return item.toString();
    }
}