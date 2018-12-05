package com.bw.movie.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaSearchActivity;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.cview.MySearchView;
import com.bw.movie.fragment.CinemaListFragment;
import com.bw.movie.mvp.view.AppDelegate;

import java.util.ArrayList;
import java.util.List;

public class CinemaFragmentPresenter extends AppDelegate{
    private Context context;
    private ImageView toLocation;
    private TextView city,recommendCinema,nearbyCinema;
    //private RelativeLayout search;
    private MySearchView search;
    private ViewPager cinemaPager;
    private List<Fragment> fragments = new ArrayList<>();
    private MainActivity activity;
    @Override
    public int getLayout() {
        return R.layout.fragment_cinema;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.context = context;
    }

    public void initView(ImageView toLocation, TextView city, MySearchView search, TextView recommendCinema, TextView nearbyCinema, ViewPager cinemaPager) {
        this.toLocation = toLocation;
        this.city = city;
        this.recommendCinema = recommendCinema;
        this.nearbyCinema = nearbyCinema;
        this.search = search;
        this.cinemaPager = cinemaPager;
    }

    @Override
    public void initData() {
        super.initData();
        activity = (MainActivity) context;

        fragments.add(new CinemaListFragment());
        fragments.add(new CinemaListFragment());

        // 适配两页内容
        cinemaPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Fragment fragment = fragments.get(i);
                if (i==0){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("flag",true);
                    fragment.setArguments(bundle);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("flag",false);
                    fragment.setArguments(bundle);
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        // 切换按钮背景
        cinemaPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    recommendCinema.setBackgroundResource(R.drawable.cinema_txt_select);
                    recommendCinema.setTextColor(context.getResources().getColor(R.color.colorFFF));
                    nearbyCinema.setBackgroundResource(R.drawable.cinema_txt_default);
                    nearbyCinema.setTextColor(context.getResources().getColor(R.color.color333));
                }else {
                    nearbyCinema.setBackgroundResource(R.drawable.cinema_txt_select);
                    nearbyCinema.setTextColor(context.getResources().getColor(R.color.colorFFF));
                    recommendCinema.setBackgroundResource(R.drawable.cinema_txt_default);
                    recommendCinema.setTextColor(context.getResources().getColor(R.color.color333));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        // 搜索
        search.setOnSearchListener(new MySearchView.OnSearchListener() {
            @Override
            public void Search(String txt) {
                Intent intent = new Intent(context, CinemaSearchActivity.class);
                intent.putExtra("searchString",txt);
                context.startActivity(intent);
            }
        });
    }

    public void toRecommend() {
        // 翻页到推荐电影
        cinemaPager.setCurrentItem(0);
        CinemaListFragment fragment = (CinemaListFragment) fragments.get(0);
        fragment.onResume();
    }

    public void toNearby() {
        cinemaPager.setCurrentItem(1);
        CinemaListFragment fragment = (CinemaListFragment) fragments.get(1);
        fragment.onResume();
    }
}
