package com.qwhiteorangeofficial.pocketbudjet.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qwhiteorangeofficial.pocketbudjet.R;

import java.util.List;


public class CustomSpinnerAdapter extends ArrayAdapter {
    private Context mContext;
    private int mTextViewResourceId;
    private List<String> mObjects;
    public static boolean flag = false;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mTextViewResourceId = textViewResourceId;
        this.mObjects = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, mTextViewResourceId, null);
        }
        if (flag) {
            TextView tv = (TextView) convertView;
            tv.setText(mObjects.get(position));
        } else {
            TextView tv = (TextView) convertView;
            tv.setText(R.string.PickCategory);
        }
        return convertView;
    }
}
