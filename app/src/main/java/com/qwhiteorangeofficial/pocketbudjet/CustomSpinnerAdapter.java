package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter {
    private Context mContext;
    private int mTextViewResourceId;
    private String[] mObjects;
    String s = "nothing";
    public static boolean flag = false;
    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects){
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mTextViewResourceId = textViewResourceId;
        this.mObjects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, mTextViewResourceId, null);
//            TextView tv = (TextView) convertView;
//            tv.setText(R.string.PickCategory);
        }
        if (flag) {
            TextView tv = (TextView) convertView;
            tv.setText(mObjects[position]);
        }
        return convertView;
    }
}
