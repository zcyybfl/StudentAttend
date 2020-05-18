package com.example.studentattend.my;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentattend.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyMenu> myMenuList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myName;
        TextView myData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myName = itemView.findViewById(R.id.my_name);
            myData = itemView.findViewById(R.id.my_data);
        }
    }

    public MyAdapter(List<MyMenu> myList) {
        myMenuList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MyMenu myMenu = myMenuList.get(position);
        holder.myName.setText(myMenu.getName());
        holder.myData.setText(myMenu.getData());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Log.d("asd","123");
                        break;
                    case 1:
                        Log.d("asd","456");
                        break;
                    case 2:
                        Log.d("asd","789");
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myMenuList.size();
    }
}
