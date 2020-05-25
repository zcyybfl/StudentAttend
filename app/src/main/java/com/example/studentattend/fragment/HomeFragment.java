package com.example.studentattend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentattend.GlideImageLoader;
import com.example.studentattend.R;
import com.example.studentattend.activity.WebViewActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnBannerListener {

    private Banner banner;
    private List<String> listPath = new ArrayList<>();
    View root;
    private String url;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();

        return root;
    }

    private void initData() {
        listPath.add("http://hsjnb.com/timg.png");
        listPath.add("http://hsjnb.com/timg2.png");
        listPath.add("http://hsjnb.com/timg3.png");
        listPath.add("http://hsjnb.com/timg4.png");
        listPath.add("http://hsjnb.com/timg5.png");
    }

    private void initView() {
        initData();
        banner = root.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader())
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(5000)
                .setImages(listPath)
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void OnBannerClick(int position) {
        switch (position) {
            case 0:
                url = "http://www.cduestc.cn";
                break;
            case 1:
                url = "https://www.runoob.com/";
                break;
            case 2:
                url = "https://www.imooc.com/";
                break;
            case 3:
                url = "https://www.w3school.com.cn/";
                break;
            case 4:
                url = "https://www.cnki.net/";
                break;
            default:
                break;
        }
        Intent webView = new Intent(getContext(),WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        webView.putExtras(bundle);
        startActivity(webView);
    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
    }
}