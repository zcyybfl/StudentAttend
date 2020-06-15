package com.example.studentattend.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.studentattend.R;
import com.example.studentattend.activity.WebViewActivity;
import com.example.studentattend.dao.HomeAppBean;

import java.util.List;

public class HomeAppAdapter extends RecyclerView.Adapter<HomeAppAdapter.ViewHolder> {

    private Context context;
    private List<HomeAppBean> homeAppBeanList;
    Bundle bundle = new Bundle();
    Intent webView;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_app);
            textView = itemView.findViewById(R.id.text_app);
        }
    }

    public HomeAppAdapter(Context context, List<HomeAppBean> homeAppBeanList) {
        this.context = context;
        this.homeAppBeanList = homeAppBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                webView = new Intent(view.getContext(), WebViewActivity.class);
                switch (position) {
                    case 0:
                        bundle.putString("url","https://www.baidu.com/");
                        break;
                    case 1:
                        bundle.putString("url","http://fanyi.youdao.com/");
                        break;
                    case 2:
                        bundle.putString("url","http://newjw.cduestc.cn:1234/");
                        break;
                    case 3:
                        bundle.putString("url","https://www.imooc.com/");
                        break;
                    case 4:
                        bundle.putString("url","https://www.runoob.com/");
                        break;
                    case 5:
                        bundle.putString("url","https://www.cnki.net/");
                        break;
                    case 6:
                        bundle.putString("url","https://www.bilibili.com/");
                        break;
                    case 7:
                        bundle.putString("url","https://www.icourse163.org/");
                        break;
                    case 8:
                        bundle.putString("url","https://www.w3school.com.cn/");
                        break;
                    default:
                        break;
                }
                webView.putExtras(bundle);
                context.startActivity(webView);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        HomeAppBean homeAppBean = homeAppBeanList.get(position);
        holder.textView.setText(homeAppBean.getName());
        Glide.with(context)
                .load(homeAppBeanList.get(position).getImageUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setImageDrawable(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return homeAppBeanList.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
        }
    }
}
