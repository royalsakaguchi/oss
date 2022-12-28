package com.example.oss.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.oss.vo.Url1;

public class ossadapter extends ArrayAdapter<Url1> {
    public ossadapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
