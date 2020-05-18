package com.example.studentattend.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentattend.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<MyMenu> {

    private int resource;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<MyMenu> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyMenu myMenu = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView myName = view.findViewById(R.id.my_name);
        TextPaint textPaint = myName.getPaint();
        //文本加粗
        textPaint.setFakeBoldText(true);
        TextView myData = view.findViewById(R.id.my_data);
        ImageView myImage = view.findViewById(R.id.my_image);
        assert myMenu != null;
        myName.setText(myMenu.getName());
        myData.setText(myMenu.getData());
        myImage.setImageResource(myMenu.getImageId());
        return view;
    }
    //覆盖父类ArrayAdapter<Fruit>的getView，让FruitAdapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}
