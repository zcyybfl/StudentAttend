package com.example.studentattend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentattend.image.GlideImageLoader;
import com.example.studentattend.R;
import com.example.studentattend.activity.WebViewActivity;
import com.example.studentattend.adapter.HomeAppAdapter;
import com.example.studentattend.dao.HomeAppBean;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnBannerListener {

    View root;
    private String url;
    private List<String> listPath = new ArrayList<>();
    private List<HomeAppBean> homeAppBeanList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initApp();
        RecyclerView recyclerView = root.findViewById(R.id.home_relativeLayout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false));
        HomeAppAdapter appAdapter = new HomeAppAdapter(getContext(),homeAppBeanList);
        recyclerView.setAdapter(appAdapter);
        return root;
    }

    private void initData() {
        listPath.add("http://hsjnb.com/AttendStudent/timg.png");
        listPath.add("http://hsjnb.com/AttendStudent/timg2.png");
        listPath.add("http://hsjnb.com/AttendStudent/timg3.png");
        listPath.add("http://hsjnb.com/AttendStudent/timg4.png");
        listPath.add("http://hsjnb.com/AttendStudent/timg5.png");
    }

    private void initView() {
        initData();
        Banner banner = root.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader())
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(5000)
                .setImages(listPath)
                .setOnBannerListener(this)
                .start();
    }

    private void initApp() {
        HomeAppBean google_chrome = new HomeAppBean("浏览器","http://hsjnb.com/AttendStudent/baidu.jpg");
        homeAppBeanList.add(google_chrome);
        HomeAppBean translate = new HomeAppBean("翻译器","http://hsjnb.com/AttendStudent/translate.jpg");
        homeAppBeanList.add(translate);
        HomeAppBean education_system = new HomeAppBean("教务系统","http://hsjnb.com/AttendStudent/educational_administration _system.jpg");
        homeAppBeanList.add(education_system);
        HomeAppBean mooc = new HomeAppBean("慕课网","http://hsjnb.com/AttendStudent/mooc.jpg");
        homeAppBeanList.add(mooc);
        HomeAppBean runoob = new HomeAppBean("菜鸟教程","http://hsjnb.com/AttendStudent/runoob.jpg");
        homeAppBeanList.add(runoob);
        HomeAppBean BiliBili = new HomeAppBean("BiliBili","http://hsjnb.com/AttendStudent/BiliBili.jpg");
        homeAppBeanList.add(BiliBili);
        HomeAppBean calculator = new HomeAppBean("计算器","http://hsjnb.com/AttendStudent/calculator.png");
        homeAppBeanList.add(calculator);
        HomeAppBean binary = new HomeAppBean("进制转换","http://hsjnb.com/AttendStudent/binary.png");
        homeAppBeanList.add(binary);
        HomeAppBean random = new HomeAppBean("随机数","http://hsjnb.com/AttendStudent/random.png");
        homeAppBeanList.add(random);
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        onCreate(null);
//    }
}