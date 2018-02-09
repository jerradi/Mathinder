package com.undersnow.mathinder;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.undersnow.mathinder.ArithmQuestion;
import com.undersnow.mathinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dc on 2/23/2017.
 */

class SwipeDeckAdapter extends ArrayAdapter<ArithmQuestion> {

    private List<ArithmQuestion> data;
    private Context context;

    public SwipeDeckAdapter(List<ArithmQuestion> data, Context context , int resource) {
     super(   context,    resource, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ArithmQuestion getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater   inflater = LayoutInflater.from( getContext());
            v= convertView = inflater.inflate(R.layout.crd_item, parent, false);
            // normally use a viewholder
           // v = inflater.inflate(R.layout.test_card, parent, false);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(),  "fonts/din.ttf");
       ((TextView) v.findViewById(R.id.tv1)).setText(data.get(position). a+"");
       ((TextView) v.findViewById(R.id.tv2)).setText(data.get(position).b+"");
       ((TextView) v.findViewById(R.id.tvop)).setText(data.get(position).getOp());
       ((TextView) v.findViewById(R.id.tv1)).setTypeface(font);
       ((TextView) v.findViewById(R.id.tv2)).setTypeface(font);
       ((TextView) v.findViewById(R.id.tvop)).setTypeface(font);
       ((TextView) v.findViewById(R.id.tvop)).setTextColor( context.getResources().getColor(data.get(position).getOpColor()));
       ((TextView) v.findViewById(R.id.tvResult)).setText(data.get(position).result+"");
       ((TextView) v.findViewById(R.id.tvResult)).setTypeface(font);
       ((TextView) v.findViewById(R.id.tvCounter)).setText("Question: "+position);
       ((TextView) v.findViewById(R.id.tvCounter)).setTypeface(font);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArithmQuestion item = (ArithmQuestion)getItem(position);
                Log.i("MainActivity", item.toString());
            }
        });

        return v;
    }

    public void setColor(int color) {
       /* ((TextView) context.findViewById(R.id.tvop)).setTextColor( context.getResources().getColor(R.color.opColor2));
   */ }
}